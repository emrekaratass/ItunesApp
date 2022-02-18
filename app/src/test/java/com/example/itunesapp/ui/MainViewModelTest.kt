package com.example.itunesapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.itunesapp.data.Result
import com.example.itunesapp.data.entity.ArticleFactory
import com.example.itunesapp.domain.usecase.CollectionParams
import com.example.itunesapp.domain.usecase.GetCollectionUseCase
import com.example.itunesapp.shared.base.BaseTestClass
import com.example.itunesapp.shared.extension.runBlockingTest
import com.example.itunesapp.shared.lifecycle.LifeCycleTestOwner
import com.example.itunesapp.shared.rule.CoroutinesTestRule
import com.example.itunesapp.ui.entity.CollectionViewItem
import com.example.itunesapp.ui.main.MainViewModel
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseTestClass() {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: GetCollectionUseCase

    @RelaxedMockK
    private lateinit var setCollectionObserver: Observer<CollectionViewItem>

    @RelaxedMockK
    private lateinit var errorObserver: Observer<String>

    private lateinit var mainViewModel: MainViewModel
    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner

    @Before
    override fun setUp() {
        super.setUp()

        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()

        mainViewModel = MainViewModel(useCase)
    }

    @After
    override fun tearDown() {
        super.tearDown()

        lifeCycleTestOwner.onDestroy()

        mainViewModel.setListLiveData.removeObserver(setCollectionObserver)
        mainViewModel.setListLiveData.removeObservers(lifeCycleTestOwner)

        mainViewModel.errorLiveData.removeObserver(errorObserver)
        mainViewModel.errorLiveData.removeObservers(lifeCycleTestOwner)
    }

    @Test
    fun `collection view item should be filled after data has been fetched`() =
        coroutinesTestRule.runBlockingTest {
            // Given
            coEvery {
                useCase.execute(
                    CollectionParams(
                        page = 0,
                        query = "harrry",
                        entity = "movie",
                        pageSize = "20"
                    )
                )
            } coAnswers {
                Result.Success(CollectionViewItem(arrayListOf(ArticleFactory.getArticle())))
            }

            lifeCycleTestOwner.onResume()

            // When
            mainViewModel.searchCollection("harrry", "movie")

            delay(1_000L)

            val setListValue = mainViewModel.setListLiveData.value
            val errorValue = mainViewModel.errorLiveData.value

            // Then
            Truth.assertThat(setListValue).isNotNull()
            Truth.assertThat(errorValue).isNull()

            coVerify(atLeast = 1) {
                useCase.execute(
                    CollectionParams(
                        page = 0,
                        query = "harrry",
                        entity = "movie",
                        pageSize = "20"
                    )
                )
            }

            confirmVerified(useCase)
        }

    @Test
    fun `error message should be filled after the exception occurred`() =
        coroutinesTestRule.runBlockingTest {
            coEvery {
                useCase.execute(
                    CollectionParams(
                        page = 0,
                        query = "harrry",
                        entity = "movie",
                        pageSize = "20"
                    )
                )
            } coAnswers {
                Result.Error(Exception("Error message!"))
            }

            lifeCycleTestOwner.onResume()

            mainViewModel.searchCollection("harrry", "movie")

            delay(1_000L)

            val setListValue = mainViewModel.setListLiveData.value
            val errorValue = mainViewModel.errorLiveData.value

            Truth.assertThat(setListValue?.results).isNull()
            Truth.assertThat(errorValue).isNotNull()
            Truth.assertThat(errorValue).isEqualTo("Error message!")

            coVerify(atLeast = 1) {
                useCase.execute(
                    CollectionParams(
                        page = 0,
                        query = "harrry",
                        entity = "movie",
                        pageSize = "20"
                    )
                )
            }

            confirmVerified(useCase)
        }

}

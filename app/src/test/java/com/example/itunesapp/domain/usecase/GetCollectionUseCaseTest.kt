package com.example.itunesapp.domain.usecase

import com.example.itunesapp.data.Result
import com.example.itunesapp.domain.entity.Collection
import com.example.itunesapp.domain.mapper.CollectionItemMapper
import com.example.itunesapp.domain.repository.CollectionRepository
import com.example.itunesapp.shared.base.BaseTestClass
import com.example.itunesapp.shared.extension.runBlockingTest
import com.example.itunesapp.shared.rule.CoroutinesTestRule
import com.example.itunesapp.ui.entity.CollectionViewItem
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCollectionUseCaseTest : BaseTestClass() {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var repository: CollectionRepository

    @MockK
    private lateinit var mapper: CollectionItemMapper

    @RelaxedMockK
    private lateinit var collection: Collection

    @RelaxedMockK
    lateinit var collectionViewItem: CollectionViewItem

    private lateinit var useCase: GetCollectionUseCase

    override fun setUp() {
        super.setUp()

        coEvery {
            repository.fetchCollection(
                CollectionParams(
                    page = 0,
                    query = "harrry",
                    entity = "movie",
                    pageSize = "20"
                )
            )
        } coAnswers {
            Result.Success(collection)
        }

        coEvery { mapper.map(any()) } returns CollectionViewItem(listOf())

        every { collectionViewItem.results } returns listOf()

        useCase = GetCollectionUseCase(repository, mapper)
    }

    @Test
    fun `use case should return data properly`() = coroutinesTestRule.runBlockingTest {
        // Given
        val collectionParams = CollectionParams(
            page = 0,
            query = "harrry",
            entity = "movie",
            pageSize = "20"
        )

        // Then
        val result = useCase.execute(collectionParams)

        // Then
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
        result as Result.Success
        Truth.assertThat(result.data.results).isNotNull()


        coVerify(exactly = 1) {
            repository.fetchCollection(
                CollectionParams(
                    page = 0,
                    query = "harrry",
                    entity = "movie",
                    pageSize = "20"
                )
            )
        }
        confirmVerified(repository)
    }
}

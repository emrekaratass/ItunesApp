package com.example.itunesapp.data.repository

import com.example.itunesapp.data.Result
import com.example.itunesapp.data.datasource.CollectionRemoteDataSource
import com.example.itunesapp.domain.entity.Article
import com.example.itunesapp.domain.entity.Collection
import com.example.itunesapp.domain.repository.CollectionRepository
import com.example.itunesapp.domain.usecase.CollectionParams
import com.example.itunesapp.shared.base.BaseTestClass
import com.example.itunesapp.shared.extension.runBlockingTest
import com.example.itunesapp.shared.rule.CoroutinesTestRule
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
class CollectionRepositoryImplTest : BaseTestClass() {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var dataSource: CollectionRemoteDataSource

    @RelaxedMockK
    private lateinit var collection: Collection

    private lateinit var repository: CollectionRepository

    override fun setUp() {
        super.setUp()

        coEvery {
            dataSource.fetchCollection(
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

        every {
            collection.articles
        } returns listOf(Article(collectionName = "collectionName"))

        repository = CollectionRepositoryImpl(dataSource)
    }

    @Test
    fun `repository should return data`() = coroutinesTestRule.runBlockingTest {
        // Given
        val collectionParams = CollectionParams(
            page = 0,
            query = "harrry",
            entity = "movie",
            pageSize = "20"
        )

        // When
        val result = repository.fetchCollection(collectionParams)

        // Then

        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
        result as Result.Success
        Truth.assertThat(result.data.articles).isNotNull()
        Truth.assertThat(result.data.articles.first().collectionName).isEqualTo("collectionName")

        coVerify(exactly = 1) {
            dataSource.fetchCollection(
                CollectionParams(
                    page = 0,
                    query = "harrry",
                    entity = "movie",
                    pageSize = "20"
                )
            )
        }
        confirmVerified(dataSource)
    }
}

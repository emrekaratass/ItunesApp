package com.example.itunesapp.data.datasource

import com.example.itunesapp.data.Result
import com.example.itunesapp.data.entity.CollectionResponse
import com.example.itunesapp.data.mapper.CollectionDomainMapper
import com.example.itunesapp.data.service.CollectionService
import com.example.itunesapp.domain.entity.Collection
import com.example.itunesapp.domain.usecase.CollectionParams
import com.example.itunesapp.shared.base.BaseTestClass
import com.example.itunesapp.shared.extension.runBlockingTest
import com.example.itunesapp.shared.rule.CoroutinesTestRule
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CollectionRemoteDataSourceImplTest : BaseTestClass() {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var collectionService: CollectionService

    @MockK
    private lateinit var mapper: CollectionDomainMapper

    private lateinit var dataSource: CollectionRemoteDataSource

    override fun setUp() {
        super.setUp()
        dataSource = CollectionRemoteDataSourceImpl(collectionService, mapper)
    }

    @Test
    fun `data source should return data`() = coroutinesTestRule.runBlockingTest {
        // Given
        coEvery {
            collectionService.getCollection(
                hashMapOf(
                    "pageSize" to "20",
                    "page" to "0",
                    "entity" to "movie",
                    "query" to "harrry"
                )
            )
        }.coAnswers {
            CollectionResponse()
        }

        val collectionParams = CollectionParams(
            page = 0,
            query = "harrry",
            entity = "movie",
            pageSize = "20"
        )

        // When
        val result: Result<Collection> = dataSource.fetchCollection(collectionParams)

        // Then
        Truth.assertThat(result).isNotNull()
    }
}

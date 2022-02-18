package com.example.itunesapp.data.mapper

import com.example.itunesapp.data.entity.ArticleResponse
import com.example.itunesapp.data.entity.CollectionResponse
import com.example.itunesapp.shared.extension.runBlockingTest
import com.example.itunesapp.shared.rule.CoroutinesTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CollectionDomainMapperTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var mapper: CollectionDomainMapper

    @Before
    fun setUp() {
        mapper = CollectionDomainMapper()
    }

    @Test
    fun `mapper should map remote data source item to domain item type properly`() =
        coroutinesTestRule.runBlockingTest {
            // Given
            val results = listOf(ArticleResponse(collectionName = "collectionName"))
            val remoteDataSourceItem = CollectionResponse(results = results)

            // When
            val domainItem = mapper.map(remoteDataSourceItem)

            // Then
            Truth.assertThat(domainItem).isNotNull()
            Truth.assertThat(domainItem.articles).isNotNull()
            Truth.assertThat(domainItem.articles.first().collectionName).isEqualTo("collectionName")
        }
}

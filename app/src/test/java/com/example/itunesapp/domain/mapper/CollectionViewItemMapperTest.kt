package com.example.itunesapp.domain.mapper

import com.example.itunesapp.domain.entity.Article
import com.example.itunesapp.domain.entity.Collection
import com.example.itunesapp.shared.extension.runBlockingTest
import com.example.itunesapp.shared.rule.CoroutinesTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CollectionViewItemMapperTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var mapper: CollectionItemMapper

    @Before
    fun setUp() {
        mapper = CollectionItemMapper()
    }

    @Test
    fun `mapper should map domain item to view item type properly`() =
        coroutinesTestRule.runBlockingTest {
            // Given
            val articles = listOf(Article(collectionName = "collectionName"))
            val domainItem = Collection(articles = articles)

            // When
            val viewItem = mapper.map(domainItem)

            // Then
            Truth.assertThat(viewItem).isNotNull()
            Truth.assertThat(viewItem.results).isNotNull()
            Truth.assertThat(viewItem.results.first().collectionName).isEqualTo("collectionName")
        }
}

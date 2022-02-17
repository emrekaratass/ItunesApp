package com.example.itunesapp.domain.mapper

import com.example.itunesapp.domain.entity.Collection
import com.example.itunesapp.ui.entity.ArticleViewItem
import com.example.itunesapp.ui.entity.CollectionViewItem
import com.example.itunesapp.util.extension.ignoreNull
import com.example.itunesapp.util.mapper.Mapper

class CollectionItemMapper : Mapper<Collection, CollectionViewItem> {

    override suspend fun map(item: Collection): CollectionViewItem {
        val articles: ArrayList<ArticleViewItem> = arrayListOf()
        item.articles.forEach { response ->
            val articleItem = ArticleViewItem(
                artworkUrl100 = response.artworkUrl100.ignoreNull(),
                collectionName = response.collectionName.ignoreNull(),
                collectionPrice = response.collectionPrice.ignoreNull(),
                releaseDate = response.releaseDate.ignoreNull(),
                description = response.description.ignoreNull()
            )
            articles.add(articleItem)
        }
        return CollectionViewItem(articles)
    }
}
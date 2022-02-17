package com.example.itunesapp.data.mapper

import com.example.itunesapp.data.entity.CollectionResponse
import com.example.itunesapp.domain.entity.Article
import com.example.itunesapp.domain.entity.Collection
import com.example.itunesapp.util.mapper.Mapper

class CollectionDomainMapper : Mapper<CollectionResponse, Collection> {

    override suspend fun map(item: CollectionResponse): Collection {
        val articles: ArrayList<Article> = arrayListOf()
        item.results?.forEach { response ->
            val article = Article(
                artworkUrl100 = response.artworkUrl100,
                collectionName = response.collectionName,
                collectionPrice = response.collectionPrice,
                releaseDate = response.releaseDate,
                description = response.description
            )
            articles.add(article)
        }
        return Collection(articles)
    }
}
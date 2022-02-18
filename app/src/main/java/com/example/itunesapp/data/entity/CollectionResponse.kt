package com.example.itunesapp.data.entity

import com.example.itunesapp.util.entity.RemoteDataSourceItem

data class CollectionResponse(
    val resultCount: Int? = null,
    val results: List<ArticleResponse>? = null
) : RemoteDataSourceItem
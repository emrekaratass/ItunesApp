package com.example.itunesapp.domain.entity

import com.example.itunesapp.util.entity.DomainItem

data class Article(
    val artworkUrl100: String? = null,
    val collectionName: String? = null,
    val collectionPrice: Double? = null,
    val releaseDate: String? = null,
    val description: String? = null
) : DomainItem
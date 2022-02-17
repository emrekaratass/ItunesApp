package com.example.itunesapp.domain.entity

import com.example.itunesapp.util.entity.DomainItem

data class Collection(
    val articles: List<Article>
) : DomainItem
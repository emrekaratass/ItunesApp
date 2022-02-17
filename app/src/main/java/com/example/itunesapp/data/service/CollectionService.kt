package com.example.itunesapp.data.service

import com.example.itunesapp.data.entity.CollectionResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CollectionService {

    @GET("search?")
    suspend fun getCollection(
        @QueryMap queryMap: Map<String, String>
    ): CollectionResponse
}
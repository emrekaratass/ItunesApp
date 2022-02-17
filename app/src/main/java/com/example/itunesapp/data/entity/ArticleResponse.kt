package com.example.itunesapp.data.entity

import com.example.itunesapp.util.entity.RemoteDataSourceItem
import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName(value = "collectionName", alternate = ["trackName"])
    val collectionName: String? = null,
    @SerializedName("collectionPrice", alternate = ["price", "trackPrice"])
    val collectionPrice: Double? = null,
    val artworkUrl100: String? = null,
    val releaseDate: String? = null,
    @SerializedName("description", alternate = ["longDescription"])
    val description: String? = null
) : RemoteDataSourceItem
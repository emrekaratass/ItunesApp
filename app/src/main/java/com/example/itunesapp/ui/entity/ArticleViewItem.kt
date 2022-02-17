package com.example.itunesapp.ui.entity

import android.os.Parcelable
import com.example.itunesapp.util.entity.ViewItem
import kotlinx.android.parcel.Parcelize

@Parcelize
class ArticleViewItem(
    val artworkUrl100: String,
    val collectionName: String,
    val collectionPrice: Double,
    val releaseDate: String,
    val description: String
) : ViewItem, Parcelable
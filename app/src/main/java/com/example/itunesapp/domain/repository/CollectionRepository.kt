package com.example.itunesapp.domain.repository

import com.example.itunesapp.data.Result
import com.example.itunesapp.domain.entity.Collection
import com.example.itunesapp.domain.usecase.CollectionParams
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    suspend fun fetchCollection(params: CollectionParams): Result<Collection>
}
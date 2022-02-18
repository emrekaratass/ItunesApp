package com.example.itunesapp.data.datasource

import com.example.itunesapp.data.Result
import com.example.itunesapp.domain.entity.Collection
import com.example.itunesapp.domain.usecase.CollectionParams
import kotlinx.coroutines.flow.Flow

interface CollectionRemoteDataSource {
    suspend fun fetchCollection(params: CollectionParams): Result<Collection>
}
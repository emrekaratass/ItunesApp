package com.example.itunesapp.data.repository

import com.example.itunesapp.data.Result
import com.example.itunesapp.data.datasource.CollectionRemoteDataSource
import com.example.itunesapp.domain.entity.Collection
import com.example.itunesapp.domain.repository.CollectionRepository
import com.example.itunesapp.domain.usecase.CollectionParams
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val remoteDataSource: CollectionRemoteDataSource
) : CollectionRepository {

    override suspend fun fetchCollection(params: CollectionParams): Flow<Result<Collection>> =
        remoteDataSource.fetchCollection(params)
}
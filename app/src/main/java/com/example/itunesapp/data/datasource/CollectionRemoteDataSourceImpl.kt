package com.example.itunesapp.data.datasource

import com.example.itunesapp.data.Result
import com.example.itunesapp.data.mapper.CollectionDomainMapper
import com.example.itunesapp.data.service.CollectionService
import com.example.itunesapp.domain.usecase.CollectionParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import com.example.itunesapp.domain.entity.Collection
import com.example.itunesapp.util.extension.ignoreNull
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CollectionRemoteDataSourceImpl @Inject constructor(
    private val service: CollectionService,
    private val mapper: CollectionDomainMapper
) : CollectionRemoteDataSource {

    override suspend fun fetchCollection(params: CollectionParams): Result<Collection> =
        try {
            val queryMap: HashMap<String, String> = hashMapOf()
            queryMap[TERM] = params.query.ignoreNull()
            queryMap[ENTITY] = params.entity.ignoreNull()
            queryMap[PAGE_OFFSET] = params.page.toString()
            queryMap[PAGE_SIZE] = params.pageSize
            val response = service.getCollection(queryMap)
            val collection = mapper.map(response)

            Result.Success(collection)

        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }

    companion object {
        private const val TERM = "term"
        private const val ENTITY = "entity"
        private const val PAGE_OFFSET = "offset"
        private const val PAGE_SIZE = "limit"
    }
}

package com.example.itunesapp.domain.usecase

import com.example.itunesapp.data.succeeded
import com.example.itunesapp.data.Result
import com.example.itunesapp.domain.mapper.CollectionItemMapper
import com.example.itunesapp.domain.repository.CollectionRepository
import com.example.itunesapp.ui.entity.CollectionViewItem
import com.example.itunesapp.util.usecase.Params
import com.example.itunesapp.util.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
class GetCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository,
    private val mapper: CollectionItemMapper
) : UseCase.FlowUseCase<CollectionParams, CollectionViewItem> {

    override suspend fun execute(params: CollectionParams): Flow<Result<CollectionViewItem>> {
        return repository.fetchCollection(params)
            .flatMapLatest { result ->
                flow {
                    if (result.succeeded) {
                        result as Result.Success
                        val viewItem = mapper.map(result.data)
                        emit(Result.Success(viewItem))
                        return@flow
                    }
                    result as Result.Error
                    emit(Result.Error(result.exception))
                }
            }.catch { throwable ->
                emit(Result.Error(Exception(throwable)))
            }.flowOn(Dispatchers.IO)
    }
}

data class CollectionParams(
    val page: Int,
    val query: String?,
    val entity: String?,
    val pageSize: String = "20"
) : Params()
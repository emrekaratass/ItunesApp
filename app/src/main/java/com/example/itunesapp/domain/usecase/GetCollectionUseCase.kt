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

    override suspend fun execute(params: CollectionParams): Result<CollectionViewItem> {
        try {
            val result = repository.fetchCollection(params)
            if (result.succeeded) {
                result as Result.Success
                val viewItem = mapper.map(result.data)

                return Result.Success(viewItem)
            }
            result as Result.Error

            return Result.Error(result.exception)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}

data class CollectionParams(
    val page: Int,
    val query: String?,
    val entity: String?,
    val pageSize: String = "20"
) : Params()
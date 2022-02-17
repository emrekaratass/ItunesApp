package com.example.itunesapp.util.usecase

import com.example.itunesapp.data.Result
import com.example.itunesapp.ui.entity.CollectionViewItem
import kotlinx.coroutines.flow.Flow

interface UseCase {

    @FunctionalInterface
    interface FlowUseCase<in P, out T> : UseCase where P : Params {
        suspend fun execute(params: P): Flow<Result<CollectionViewItem>>
    }
}

abstract class Params
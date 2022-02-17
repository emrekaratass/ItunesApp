package com.example.itunesapp.di

import com.example.itunesapp.domain.usecase.GetCollectionUseCase
import com.example.itunesapp.util.usecase.UseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetCollectionUseCase(useCase: GetCollectionUseCase): UseCase
}
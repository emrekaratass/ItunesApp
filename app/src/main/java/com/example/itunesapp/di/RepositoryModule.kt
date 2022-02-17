package com.example.itunesapp.di

import com.example.itunesapp.data.repository.CollectionRepositoryImpl
import com.example.itunesapp.domain.repository.CollectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCollectionRepository(repository: CollectionRepositoryImpl): CollectionRepository
}
package com.example.itunesapp.di

import com.example.itunesapp.data.datasource.CollectionRemoteDataSource
import com.example.itunesapp.data.datasource.CollectionRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindCollectionRemoteDataSource(remoteDataSource: CollectionRemoteDataSourceImpl): CollectionRemoteDataSource
}
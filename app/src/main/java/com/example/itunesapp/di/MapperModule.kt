package com.example.itunesapp.di

import com.example.itunesapp.data.mapper.CollectionDomainMapper
import com.example.itunesapp.domain.mapper.CollectionItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    fun providesCollectionDomainMapper(): CollectionDomainMapper = CollectionDomainMapper()

    @Provides
    fun providesCollectionItemMapper(): CollectionItemMapper = CollectionItemMapper()
}
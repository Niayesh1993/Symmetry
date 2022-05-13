package com.zohre.symmetry.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.zohre.data.discovery.trackList.TrackListRepository
import xyz.zohre.data.discovery.trackList.TrackListRepositoryImpl
import xyz.zohre.data.discovery.trackSearch.TrackSearchRepository
import xyz.zohre.data.discovery.trackSearch.TrackSearchRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
class DataModules {

    @Provides
    fun provideTrackListRepository(movieListRepositoryImpl: TrackListRepositoryImpl): TrackListRepository {
        return movieListRepositoryImpl
    }

    @Provides
    fun provideTrackDetailRepository(movieDetailRepositoryImpl: TrackSearchRepositoryImpl): TrackSearchRepository {
        return movieDetailRepositoryImpl
    }
}
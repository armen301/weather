package com.e.weatherapp.di.modules

import com.e.weatherapp.data.local.dataSource.LocalDataSource
import com.e.weatherapp.data.network.dataSource.RemoteDataSource
import com.e.weatherapp.data.repository.WeatherRepoImpl
import com.e.weatherapp.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @ExperimentalCoroutinesApi
    @Provides
    fun provideWeatherRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): WeatherRepository = WeatherRepoImpl(localDataSource, remoteDataSource)
}
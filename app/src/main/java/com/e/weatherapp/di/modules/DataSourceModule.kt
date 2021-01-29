package com.e.weatherapp.di.modules

import com.e.weatherapp.data.local.dataSource.LocalDataSource
import com.e.weatherapp.data.local.dataSource.LocalDataSourceImpl
import com.e.weatherapp.data.network.dataSource.RemoteDataSource
import com.e.weatherapp.data.network.dataSource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
}
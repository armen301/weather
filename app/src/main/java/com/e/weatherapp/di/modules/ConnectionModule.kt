package com.e.weatherapp.di.modules

import android.content.Context
import android.net.ConnectivityManager
import com.e.weatherapp.provider.ConnectionProvider
import com.e.weatherapp.provider.ConnectionProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ConnectionModule {

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Singleton
    @Provides
    fun provideConnectionProvider(connectionProvider: ConnectionProviderImpl): ConnectionProvider {
        return connectionProvider
    }
}
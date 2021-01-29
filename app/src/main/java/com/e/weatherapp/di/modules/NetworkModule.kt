package com.e.weatherapp.di.modules

import com.e.weatherapp.BuildConfig
import com.e.weatherapp.data.network.DataResultCallAdapterFactory
import com.e.weatherapp.data.network.NetworkService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(DataResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(logging)
        }.build()
    }
}
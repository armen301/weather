package com.e.weatherapp.di.modules

import android.content.Context
import com.e.weatherapp.data.local.db.WeatherDB
import com.e.weatherapp.data.local.db.dao.CurrentWeatherDao
import com.e.weatherapp.data.local.db.dao.DailyWeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): WeatherDB {
        return WeatherDB.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideCurrentWeatherDao(db: WeatherDB): CurrentWeatherDao {
        return db.currentWeatherDao()
    }

    @Singleton
    @Provides
    fun provideDailyWeatherDao(db: WeatherDB): DailyWeatherDao {
        return db.dailyWeatherDao()
    }
}
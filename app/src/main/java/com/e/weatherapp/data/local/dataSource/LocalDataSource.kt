package com.e.weatherapp.data.local.dataSource

import androidx.lifecycle.LiveData
import com.e.weatherapp.data.local.entities.CurrentWeather
import com.e.weatherapp.data.local.entities.DailyWeather
import java.time.LocalDateTime

interface LocalDataSource {
    fun getCurrentWeather(): LiveData<CurrentWeather>
    suspend fun saveCurrentWeather(currentWeather: CurrentWeather)

    fun getDailyForecast(dates: List<LocalDateTime>): LiveData<List<DailyWeather>>
    suspend fun saveDailyForecast(dailyWeather: List<DailyWeather>)
}
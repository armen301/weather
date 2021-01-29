package com.e.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.e.weatherapp.data.DataResult
import com.e.weatherapp.data.local.entities.CurrentWeather
import com.e.weatherapp.data.local.entities.DailyWeather
import java.time.LocalDateTime

interface WeatherRepository {
    fun loadCurrentWeather(
        latitude: Double,
        longitude: Double
    ): LiveData<DataResult<CurrentWeather>>

    fun loadDailyWeather(
        latitude: Double,
        longitude: Double,
        dates: List<LocalDateTime>
    ): LiveData<DataResult<List<DailyWeather>>>
}
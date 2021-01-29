package com.e.weatherapp.data.network.dataSource

import com.e.weatherapp.data.DataResult
import com.e.weatherapp.data.network.dto.WeatherDto

interface RemoteDataSource {
    suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ): DataResult<WeatherDto>

    suspend fun fetchDailyForecast(latitude: Double, longitude: Double): DataResult<WeatherDto>
}
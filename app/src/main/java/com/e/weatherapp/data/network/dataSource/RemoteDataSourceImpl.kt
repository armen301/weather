package com.e.weatherapp.data.network.dataSource

import com.e.weatherapp.BuildConfig
import com.e.weatherapp.data.DataResult
import com.e.weatherapp.data.network.NetworkService
import com.e.weatherapp.data.network.dto.WeatherDto
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val networkService: NetworkService) :
    RemoteDataSource {

    override suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ): DataResult<WeatherDto> {
        val query = mapOf(
            "lon" to longitude.toString(),
            "lat" to latitude.toString(),
            "appid" to BuildConfig.API_APP_ID,
            "units" to "metric",
            "exclude" to "minutely,hourly,daily,alerts"
        )
        return networkService.fetchWeather(query)
    }

    override suspend fun fetchDailyForecast(
        latitude: Double,
        longitude: Double
    ): DataResult<WeatherDto> {
        val query = mapOf(
            "lon" to longitude.toString(),
            "lat" to latitude.toString(),
            "appid" to BuildConfig.API_APP_ID,
            "units" to "metric",
            "exclude" to "minutely,hourly,current,alertss"
        )
        return networkService.fetchWeather(query)
    }
}
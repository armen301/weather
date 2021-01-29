package com.e.weatherapp.data.network

import com.e.weatherapp.data.DataResult
import com.e.weatherapp.data.network.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetworkService {

    @GET("onecall")
    suspend fun fetchWeather(@QueryMap query: Map<String, String>): DataResult<WeatherDto>

}
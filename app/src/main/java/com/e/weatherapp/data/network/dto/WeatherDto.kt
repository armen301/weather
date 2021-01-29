package com.e.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

class WeatherDto(
    @field:SerializedName("daily")
    val daily: List<DailyItem>? = null,

    @field:SerializedName("current")
    val currentWeather: CurrentWeather? = null
)
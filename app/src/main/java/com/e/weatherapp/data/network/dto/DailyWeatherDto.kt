package com.e.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

class DailyWeatherDto {
    @field:SerializedName("daily")
    val daily: List<DailyItem>? = null
}
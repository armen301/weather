package com.e.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

class CurrentWeatherDto {
    @field:SerializedName("current")
    val currentWeather: CurrentWeather? = null
}

class Temperature(

    @field:SerializedName("min")
    val min: Double? = null,

    @field:SerializedName("max")
    val max: Double? = null,

    @field:SerializedName("eve")
    val eve: Double? = null,

    @field:SerializedName("night")
    val night: Double? = null,

    @field:SerializedName("day")
    val day: Double? = null,

    @field:SerializedName("morn")
    val morn: Double? = null
)

class DailyItem(

    @field:SerializedName("rain")
    val rain: Double? = null, // Precipitation volume, mm

    @field:SerializedName("sunrise")
    val sunrise: Int? = null,

    @field:SerializedName("temp")
    val temperature: Temperature? = null,

    @field:SerializedName("uvi")
    val uvi: Double? = null,

    @field:SerializedName("pressure")
    val pressure: Int? = null,

    @field:SerializedName("clouds")
    val clouds: Int? = null,

    @field:SerializedName("feels_like")
    val feelsLike: FeelsLike? = null,

    @field:SerializedName("dt")
    val time: Long? = null, // time in seconds UTC

    @field:SerializedName("wind_deg")
    val windDeg: Int? = null,

    @field:SerializedName("dew_point")
    val dewPoint: Double? = null,

    @field:SerializedName("sunset")
    val sunset: Int? = null,

    @field:SerializedName("weather")
    val weather: List<WeatherItem?>? = null,

    @field:SerializedName("humidity")
    val humidity: Int? = null,

    @field:SerializedName("wind_speed")
    val windSpeed: Double? = null
)

class CurrentWeather(

    @field:SerializedName("sunrise")
    val sunrise: Int? = null,

    @field:SerializedName("temp")
    val temp: Double = 0.0,

    @field:SerializedName("visibility")
    val visibility: Int? = null,

    @field:SerializedName("uvi")
    val uvi: Double = 0.0,

    @field:SerializedName("pressure")
    val pressure: Int = 0,

    @field:SerializedName("clouds")
    val clouds: Int? = null,

    @field:SerializedName("feels_like")
    val feelsLike: Double? = null,

    @field:SerializedName("dt")
    val time: Long? = null, // time in seconds UTC

    @field:SerializedName("wind_deg")
    val windDeg: Int? = null,

    @field:SerializedName("dew_point")
    val dewPoint: Double? = null,

    @field:SerializedName("sunset")
    val sunset: Int? = null,

    @field:SerializedName("weather")
    val weather: List<WeatherItem?>? = null,

    @field:SerializedName("humidity")
    val humidity: Int? = null,

    @field:SerializedName("wind_speed")
    val windSpeed: Double? = null
)

class WeatherItem(

    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("main")
    val main: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

class FeelsLike(

    @field:SerializedName("eve")
    val eve: Double? = null,

    @field:SerializedName("night")
    val night: Double? = null,

    @field:SerializedName("day")
    val day: Double? = null,

    @field:SerializedName("morn")
    val morn: Double? = null
)
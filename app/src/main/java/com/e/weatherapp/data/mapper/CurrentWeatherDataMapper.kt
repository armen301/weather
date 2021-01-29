package com.e.weatherapp.data.mapper

import com.e.weatherapp.data.local.entities.CurrentWeather
import com.e.weatherapp.data.local.entities.DailyWeather
import com.e.weatherapp.data.network.dto.WeatherDto
import kotlin.math.roundToInt

fun mapCurrentWeatherDto(input: WeatherDto): CurrentWeather {
    return CurrentWeather(
        time = input.currentWeather?.time,
        temp = input.currentWeather?.temp?.roundToInt() ?: 0,
        iconUrl = input.currentWeather?.weather?.get(0)?.icon,
        feelsLike = input.currentWeather?.feelsLike?.roundToInt() ?: 0
    )
}

inline fun <I, O> mapList(input: List<I>?, mapListItem: (I) -> O): List<O> {
    return input?.map { mapListItem(it) } ?: emptyList()
}

fun mapDailyDto(input: WeatherDto): List<DailyWeather> {
    return mapList(input.daily) {
        DailyWeather(
            time = it.time,
            night = it.temperature?.night?.roundToInt() ?: 0,
            day = it.temperature?.day?.roundToInt() ?: 0,
            iconUrl = it.weather?.get(0)?.icon
        )
    }
}
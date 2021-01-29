package com.e.weatherapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.e.weatherapp.data.DataResult
import com.e.weatherapp.data.local.entities.CurrentWeather
import com.e.weatherapp.data.local.entities.DailyWeather
import com.e.weatherapp.data.repository.WeatherRepository
import java.time.LocalDateTime


class MainViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    lateinit var currentWeather: LiveData<DataResult<CurrentWeather>>
    lateinit var dailyWeather: LiveData<DataResult<List<DailyWeather>>>


    fun loadWeatherData(latitude: Double, longitude: Double) {

        currentWeather = repository.loadCurrentWeather(latitude, longitude)

        val today = LocalDateTime.now()
        val now = LocalDateTime.of(today.year, today.month, today.dayOfMonth, 13, 0, 0)

        val days = listOf(
            now,
            now.plusDays(1),
            now.plusDays(2),
            now.plusDays(3),
            now.plusDays(4),
            now.plusDays(5),
            now.plusDays(6)
        )

        dailyWeather = repository.loadDailyWeather(latitude, longitude, days)
    }

}
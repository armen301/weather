package com.e.weatherapp.data.local.dataSource

import androidx.lifecycle.LiveData
import com.e.weatherapp.data.local.db.dao.CurrentWeatherDao
import com.e.weatherapp.data.local.db.dao.DailyWeatherDao
import com.e.weatherapp.data.local.entities.CurrentWeather
import com.e.weatherapp.data.local.entities.DailyWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LocalDataSourceImpl @Inject constructor(
    private val currentWeatherDao: CurrentWeatherDao,
    private val dailyWeatherDao: DailyWeatherDao
) : LocalDataSource {
    override fun getCurrentWeather(): LiveData<CurrentWeather> = currentWeatherDao.get()

    override suspend fun saveCurrentWeather(currentWeather: CurrentWeather) {
        coroutineScope {
            withContext(Dispatchers.IO) { currentWeatherDao.insert(currentWeather) }
        }
    }

    override fun getDailyForecast(dates: List<LocalDateTime>): LiveData<List<DailyWeather>> {
        val time = LongArray(dates.size)
        val now = LocalDateTime.now()
        dates.forEachIndexed { index, localDate ->
            time[index] = localDate.toEpochSecond(ZoneId.systemDefault().rules.getOffset(now))
        }
        return dailyWeatherDao.getAll(time)
    }

    override suspend fun saveDailyForecast(dailyWeather: List<DailyWeather>) {
        coroutineScope {
            withContext(Dispatchers.IO) {
                val newDays = ArrayList<DailyWeather>()
                dailyWeather.forEach {
                    val day = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(
                            it.time ?: System.currentTimeMillis()
                        ), ZoneId.of(TimeZone.getDefault().id)
                    ).toLocalDate()
                    newDays.add(
                        DailyWeather(time = day.toEpochDay(), it.night, it.day, it.iconUrl)
                    )
                }

                dailyWeatherDao.insertAll(dailyWeather)
            }
        }
    }
}
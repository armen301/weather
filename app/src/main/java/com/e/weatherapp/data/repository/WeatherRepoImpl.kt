package com.e.weatherapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.e.weatherapp.data.DataResult
import com.e.weatherapp.data.local.dataSource.LocalDataSource
import com.e.weatherapp.data.local.entities.CurrentWeather
import com.e.weatherapp.data.local.entities.DailyWeather
import com.e.weatherapp.data.mapper.mapCurrentWeatherDto
import com.e.weatherapp.data.mapper.mapDailyDto
import com.e.weatherapp.data.network.dataSource.RemoteDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import java.time.LocalDateTime
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : WeatherRepository {

    override fun loadCurrentWeather(
        latitude: Double,
        longitude: Double
    ): LiveData<DataResult<CurrentWeather>> = performGetOperation(
        databaseQuery = { localDataSource.getCurrentWeather() },
        networkCall = { remoteDataSource.fetchCurrentWeather(latitude, longitude) },
        saveCallResult = { localDataSource.saveCurrentWeather(mapCurrentWeatherDto(it)) }
    )

    override fun loadDailyWeather(
        latitude: Double,
        longitude: Double,
        dates: List<LocalDateTime>
    ): LiveData<DataResult<List<DailyWeather>>> = performGetOperation(
        databaseQuery = { localDataSource.getDailyForecast(dates) },
        networkCall = { remoteDataSource.fetchDailyForecast(latitude, longitude) },
        saveCallResult = { localDataSource.saveDailyForecast(mapDailyDto(it)) }
    )
}

fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> DataResult<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<DataResult<T>> =
    liveData(CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
    } + Dispatchers.IO) {
        emit(DataResult.Loading())
        val source: LiveData<DataResult<T>> = databaseQuery.invoke().map { DataResult.Success(it) }
        emitSource(source)

        when (val responseStatus = networkCall()) {
            is DataResult.Success -> {
                saveCallResult(responseStatus.data)
                val newSource: LiveData<DataResult<T>> =
                    databaseQuery.invoke().map { DataResult.Success(it) }
                emitSource(newSource)
            }
            is DataResult.Error -> {
                emit(DataResult.Error(responseStatus.message))
            }
        }
    }
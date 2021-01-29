package com.e.weatherapp.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.e.weatherapp.data.local.entities.CurrentWeather

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: CurrentWeather)

    @Query("SELECT * FROM current ORDER BY dt DESC LIMIT 1")
    fun get(): LiveData<CurrentWeather>
}
package com.e.weatherapp.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.e.weatherapp.data.local.entities.DailyWeather

@Dao
interface DailyWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dailyWeathers: List<DailyWeather>)

    @Query("SELECT * FROM daily WHERE dt IN (:timeDuration)")
    fun getAll(timeDuration: LongArray): LiveData<List<DailyWeather>>
}
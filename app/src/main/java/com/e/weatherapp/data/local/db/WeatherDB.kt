package com.e.weatherapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.e.weatherapp.data.local.db.dao.CurrentWeatherDao
import com.e.weatherapp.data.local.db.dao.DailyWeatherDao
import com.e.weatherapp.data.local.entities.CurrentWeather
import com.e.weatherapp.data.local.entities.DailyWeather

@Database(
    entities = [CurrentWeather::class, DailyWeather::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDB : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun dailyWeatherDao(): DailyWeatherDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: WeatherDB? = null

        fun getDatabase(context: Context): WeatherDB {
            // if the INSTANCE is not null, then return it, if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, WeatherDB::class.java, "weather_db")
                .fallbackToDestructiveMigration()
                .build()
    }

}
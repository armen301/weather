package com.e.weatherapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current")
class CurrentWeather(

    @PrimaryKey
    @ColumnInfo(name = "dt")
    val time: Long? = null, // time in seconds UTC

    @ColumnInfo(name = "temp")
    val temp: Int = 0,

    @ColumnInfo(name = "feels_like")
    val feelsLike: Int = 0,

    @ColumnInfo(name = "icon")
    val iconUrl: String? = null,
)
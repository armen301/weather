package com.e.weatherapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily")
class DailyWeather(

    @PrimaryKey
    @ColumnInfo(name = "dt")
    val time: Long? = null,

    @ColumnInfo(name = "nightTemp")
    val night: Int = 0,

    @ColumnInfo(name = "dayTemp")
    val day: Int = 0,

    @ColumnInfo(name = "icon")
    val iconUrl: String? = null
)
package com.e.weatherapp.provider

interface ConnectionProvider {
    fun isConnected(): Boolean
}
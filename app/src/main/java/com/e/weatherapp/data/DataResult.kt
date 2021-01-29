package com.e.weatherapp.data

// A generic class that contains data and status about loading this data.
sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    class SuccessEmptyBody<out T> : DataResult<T>()
    data class Error(val message: String, val code: Int = -1) : DataResult<Nothing>()
    data class Loading<out T>(val data: T? = null) : DataResult<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$message] \ncode = $code"
            is Loading<*> -> "Loading[data=$data]"
            is SuccessEmptyBody -> "SuccessEmptyBody"
        }
    }
}
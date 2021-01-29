package com.e.weatherapp.data.network

import com.e.weatherapp.data.DataResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class DataResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != DataResult::class.java) return null

        check(responseType is ParameterizedType) { "Response must be parameterized" }

        val successBodyType = getParameterUpperBound(0, responseType)

        return DataResultCallAdapter<Any>(successBodyType)
    }

    companion object {
        @JvmStatic
        fun create() = DataResultCallAdapterFactory()
    }
}
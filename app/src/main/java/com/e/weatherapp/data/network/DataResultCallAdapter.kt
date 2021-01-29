package com.e.weatherapp.data.network

import com.e.weatherapp.data.DataResult
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.Type

class DataResultCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Call<DataResult<R>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Call<DataResult<R>> = DataResultCall(call)
}

internal class DataResultCall<R>(
    private val delegate: Call<R>
) : Call<DataResult<R>> {
    override fun clone(): Call<DataResult<R>> = DataResultCall(delegate.clone())

    override fun execute(): Response<DataResult<R>> {
        throw UnsupportedOperationException("DataResultCall doesn't support execute")
    }

    override fun enqueue(callback: Callback<DataResult<R>>) {
        return delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {

                if (!response.isSuccessful) {
                    callback.onResponse(
                        this@DataResultCall,
                        Response.success(
                            DataResult.Error(
                                response.errorBody()?.string() ?: "Unknown error",
                                response.code()
                            )
                        )
                    )
                    return
                }

                response.body()?.let {
                    callback.onResponse(
                        this@DataResultCall,
                        Response.success(DataResult.Success(it))
                    )
                    return
                }

                val newResponse: Response<DataResult<R>> = when (val code = response.code()) {
                    204 -> Response.success(DataResult.SuccessEmptyBody())
                    else -> Response.success(DataResult.Error("Unknown error", code))
                }

                callback.onResponse(this@DataResultCall, newResponse)
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                callback.onResponse(
                    this@DataResultCall,
                    Response.success(DataResult.Error(t.message ?: "Unknown error"))
                )
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
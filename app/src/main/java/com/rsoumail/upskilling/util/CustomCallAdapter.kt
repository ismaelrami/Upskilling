package com.rsoumail.upskilling.util

import com.rsoumail.upskilling.data.remote.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type


/**
 * A Retrofit adapter that converts the Call into a ApiResponse of R.
 * @param <R>
</R> */
class CustomCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Call<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Call<ApiResponse<R>> = ApiResponseCall(call)
}

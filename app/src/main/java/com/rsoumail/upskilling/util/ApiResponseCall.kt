package com.rsoumail.upskilling.util

import com.rsoumail.upskilling.data.remote.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiResponseCall<T>(proxy: Call<T>) : CallDelegate<T, ApiResponse<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<ApiResponse<T>>) = proxy.enqueue(object: Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            callback.onResponse(this@ApiResponseCall, Response.success(ApiResponse.create(response)))
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            callback.onResponse(this@ApiResponseCall, Response.success(ApiResponse.create(throwable)))
        }
    })

    override fun cloneImpl() = ApiResponseCall(proxy.clone())
}
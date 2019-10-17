package com.rsoumail.upskilling.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.rsoumail.upskilling.common.Resource
import com.rsoumail.upskilling.data.remote.ApiEmptyResponse
import com.rsoumail.upskilling.data.remote.ApiErrorResponse
import com.rsoumail.upskilling.data.remote.ApiResponse
import com.rsoumail.upskilling.data.remote.ApiSuccessResponse
import kotlinx.coroutines.Dispatchers

fun <T, A> resultLiveData(databaseQuery: () -> LiveData<T>,
                          shouldFetchData: (LiveData<Resource<T>>) -> Boolean,
                          networkCall: suspend () -> ApiResponse<A>,
                          onFetchFailed: () -> Unit,
                          saveCallResult: suspend (A) -> Unit,
                          processResponse: (ApiSuccessResponse<A>) -> A
                          ): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        val source = databaseQuery.invoke().map { Resource.success(it) }
        if(shouldFetchData(source)) {
            when(val response = networkCall.invoke()) {
                is ApiSuccessResponse -> {
                    saveCallResult(processResponse(response))
                    emitSource(databaseQuery.invoke().map { Resource.success(it) })
                }
                is ApiEmptyResponse -> {
                    emitSource(source)
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    emit(Resource.error(response.errorMessage, null))
                    emitSource(source)
                }
            }
        } else {
            emitSource(source)
        }

    }
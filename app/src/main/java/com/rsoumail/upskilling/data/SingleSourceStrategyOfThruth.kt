package com.rsoumail.upskilling.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.rsoumail.upskilling.common.Resource
import com.rsoumail.upskilling.common.Status
import com.rsoumail.upskilling.data.remote.ApiEmptyResponse
import com.rsoumail.upskilling.data.remote.ApiErrorResponse
import com.rsoumail.upskilling.data.remote.ApiResponse
import com.rsoumail.upskilling.data.remote.ApiSuccessResponse
import kotlinx.coroutines.*

suspend fun <T, A> result(databaseQuery: suspend () -> T,
                          shouldFetchData: (T?) -> Boolean,
                          netWorkCall: suspend () -> Resource<A>,
                          saveCallResult: suspend (A) -> Unit,
                          processResponse: (Resource<A>) -> A): Resource<T> {
    val fromDb = databaseQuery()
    var result : Resource<T> = Resource.success(fromDb)
    if(shouldFetchData(fromDb)){
        val response = netWorkCall()
        if (response.status == Status.SUCCESS && response.data != null) {
            saveCallResult(processResponse(response))
            return Resource.success(databaseQuery())
        }
        if (response.status == Status.SUCCESS && response.data == null){
            return Resource.success(fromDb)
        }

        if (response.status == Status.ERROR) {
            return Resource.error(response.message!!, fromDb)
        }
    }

    return result
}


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

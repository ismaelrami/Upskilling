package com.rsoumail.upskilling.data.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.rsoumail.upskilling.AppExecutors
import com.rsoumail.upskilling.common.Resource


/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class CoroutineNetworkBoundResource<ResultType, RequestType>
@MainThread constructor() {

    private var result: LiveData<Resource<ResultType>>

    init {
        result = liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            val source = loadFromDb().map { Resource.success(it) }
            if (shouldFetch(source.value?.data)) {
                fetchFromNetWork()
                when(val response = fetchFromNetWork()) {
                    is ApiSuccessResponse -> {
                        saveCallResult(processResponse(response))
                        emitSource(loadFromDb().map { Resource.success(it) })
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
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        /*if (result.value != newValue) {
            result.value = newValue
        }*/
    }


    /* private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        return createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }*/

    protected open fun onFetchFailed() {}

    fun asLiveData() = result

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract suspend fun fetchFromNetWork(): ApiResponse<RequestType>
}
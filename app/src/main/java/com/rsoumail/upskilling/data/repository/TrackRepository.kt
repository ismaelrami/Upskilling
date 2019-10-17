package com.rsoumail.upskilling.data.repository

import androidx.lifecycle.LiveData
import com.rsoumail.upskilling.AppExecutors
import com.rsoumail.upskilling.common.Resource
import com.rsoumail.upskilling.data.local.AppDb
import com.rsoumail.upskilling.data.local.TrackSearchResult
import com.rsoumail.upskilling.data.remote.*

/**
 * Repository that handles Repo instances.
 *
 * unfortunate naming :/ .
 * Repo - value object name
 * Repository - type of this class.
 */

class TrackRepository constructor(
    private val appExecutors: AppExecutors,
    private val db: AppDb,
    private val trackService: TrackService
) {


     fun search(query: String): LiveData<Resource<TrackSearchResult>> {
        return object : CoroutineNetworkBoundResource<TrackSearchResult, TrackSearchResponse>() {

            override fun saveCallResult(item: TrackSearchResponse) {
                val trackSearchResult = TrackSearchResult(
                    query = query,
                    totalCount = item.count,
                    tracks = item.results
                )
                db.runInTransaction {
                    db.trackDao().insert(trackSearchResult)
                }
            }

            override fun shouldFetch(data: TrackSearchResult?) = data == null

            override fun loadFromDb(): LiveData<TrackSearchResult> {
                return db.trackDao().search(query)
            }

            override suspend fun fetchFromNetWork() = trackService.search(query)

            override fun processResponse(response: ApiSuccessResponse<TrackSearchResponse>)
                    : TrackSearchResponse {
                return response.body
            }
        }.asLiveData()
    }

    /*fun search(query: String): LiveData<Resource<TrackSearchResult>> {
        return resultLiveData(
            databaseQuery = { db.trackDao().search(query)},
            shouldFetchData = { data == null },
            networkCall = { trackService.search(query) },
            onFetchFailed = {},
            saveCallResult = {
                val trackSearchResult = TrackSearchResult(
                    query = query,
                    totalCount = it.count,
                    tracks = it.results
                )
                db.runInTransaction {
                    db.trackDao().insert(trackSearchResult)
                }
            },
            processResponse = {
                it.body
            }
        )
    }*/
}
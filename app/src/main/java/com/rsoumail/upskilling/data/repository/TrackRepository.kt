package com.rsoumail.upskilling.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.rsoumail.upskilling.AppExecutors
import com.rsoumail.upskilling.common.Resource
import com.rsoumail.upskilling.data.local.AppDb
import com.rsoumail.upskilling.data.local.TrackDao
import com.rsoumail.upskilling.data.local.TrackSearchResult
import com.rsoumail.upskilling.data.remote.ApiSuccessResponse
import com.rsoumail.upskilling.data.remote.NetworkBoundResource
import com.rsoumail.upskilling.data.remote.TrackService
import com.rsoumail.upskilling.data.remote.TrackSearchResponse
import com.rsoumail.upskilling.domain.entity.Track
import com.rsoumail.upskilling.util.AbsentLiveData

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
    private val trackDao: TrackDao,
    private val trackService: TrackService
) {


    fun search(query: String): LiveData<Resource<TrackSearchResult>> {
        return object : NetworkBoundResource<TrackSearchResult, TrackSearchResponse>(appExecutors) {

            override fun saveCallResult(item: TrackSearchResponse) {
                val trackSearchResult = TrackSearchResult(
                    query = query,
                    totalCount = item.count,
                    tracks = item.results
                )
                db.runInTransaction {
                    trackDao.insert(trackSearchResult)
                }
            }

            override fun shouldFetch(data: TrackSearchResult?) = data == null

            override fun loadFromDb(): LiveData<TrackSearchResult> {
                return trackDao.search(query)
            }

            override fun createCall() = trackService.search(query)

            override fun processResponse(response: ApiSuccessResponse<TrackSearchResponse>)
                    : TrackSearchResponse {
                return response.body
            }
        }.asLiveData()
    }
}
package com.rsoumail.upskilling.data.repository


import com.rsoumail.upskilling.common.Resource
import com.rsoumail.upskilling.data.local.AppDb
import com.rsoumail.upskilling.data.local.TrackSearchResult
import com.rsoumail.upskilling.data.remote.*
import com.rsoumail.upskilling.data.result
import com.rsoumail.upskilling.domain.repositoty.TrackRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Repository that handles Repo instances.
 *
 * unfortunate naming :/ .
 * Repo - value object name
 * Repository - type of this class.
 */

class TrackRepositoryImpl constructor(
    private val db: AppDb,
    private val remoteDataSource: RemoteDataSource
) : TrackRepository {
    override suspend fun search(query: String) : Resource<TrackSearchResult> {
        return result(
            databaseQuery = { db.trackDao().search(query)},
            shouldFetchData = { it == null },
            netWorkCall = {remoteDataSource.search(query)} ,
            saveCallResult = {
                val trackSearchResult = TrackSearchResult(
                    query = query,
                    totalCount = it.count,
                    tracks = it.results
                )
                db.trackDao().insert(trackSearchResult)
            },
            processResponse = {
                it.data!!
            }
        )
    }
}
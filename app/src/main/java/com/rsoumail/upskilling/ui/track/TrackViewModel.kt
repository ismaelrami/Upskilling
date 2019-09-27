package com.rsoumail.upskilling.ui.track

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.rsoumail.upskilling.common.Resource
import com.rsoumail.upskilling.data.local.TrackSearchResult
import com.rsoumail.upskilling.data.repository.TrackRepository
import com.rsoumail.upskilling.util.AbsentLiveData
import com.rsoumail.upskilling.data.remote.ApiResponse
import com.rsoumail.upskilling.data.remote.TrackSearchResponse
import com.rsoumail.upskilling.domain.entity.Track
import com.rsoumail.upskilling.util.Api
import androidx.room.Room
import com.rsoumail.upskilling.AppExecutors
import com.rsoumail.upskilling.data.local.AppDb
import java.util.*

class TrackViewModel(trackRepository: TrackRepository) : ViewModel(){

    // val trackRepository: TrackRepository = TrackRepository(AppExecutors(), appDb, appDb.trackDao(), Api().getTrackService()!!)
    private val _query = MutableLiveData<String>()
    private val result = MediatorLiveData<ApiResponse<TrackSearchResponse>>()
    val query: LiveData<String> = _query
    // val results: LiveData<Resource<TrackSearchResult>> = trackRepository.search(query)

    val results: LiveData<Resource<TrackSearchResult>> = Transformations
    .switchMap(_query) { search ->
        if (search.isNullOrBlank()) {
            AbsentLiveData.create()
        } else {
            trackRepository.search(search)
        }
    }

    /*val results: LiveData<ApiResponse<TrackSearchResponse>> = Transformations
        .switchMap(_query) { search ->
            Api().getTrackService()!!.search(search)
        }*/

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        _query.value = input
    }

}
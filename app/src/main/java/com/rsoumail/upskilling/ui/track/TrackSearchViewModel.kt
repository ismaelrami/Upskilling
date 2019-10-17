package com.rsoumail.upskilling.ui.track


import androidx.lifecycle.*
import com.rsoumail.upskilling.common.Resource
import com.rsoumail.upskilling.data.local.TrackSearchResult
import com.rsoumail.upskilling.data.repository.TrackRepository
import com.rsoumail.upskilling.util.AbsentLiveData
import java.util.*

class TrackSearchViewModel(trackRepository: TrackRepository) : ViewModel(){

    private val _query = MutableLiveData<String>()
    // val query: LiveData<String> = _query

    val results: LiveData<Resource<TrackSearchResult>> = Transformations
    .switchMap(_query) { search ->
        if (search.isNullOrBlank()) {
            AbsentLiveData.create()
        } else {
            trackRepository.search(search)
        }
    }

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        _query.value = input
    }

}
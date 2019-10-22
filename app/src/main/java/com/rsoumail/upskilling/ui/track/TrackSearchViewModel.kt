package com.rsoumail.upskilling.ui.track

import androidx.lifecycle.*
import com.rsoumail.upskilling.common.Resource
import com.rsoumail.upskilling.data.local.TrackSearchResult
import com.rsoumail.upskilling.domain.interactor.SearchTrackUseCase
import com.rsoumail.upskilling.util.AbsentLiveData
import kotlinx.coroutines.Dispatchers
import java.util.*

class TrackSearchViewModel(private val searchTrackUseCase: SearchTrackUseCase) : ViewModel(){

    private val _query = MutableLiveData<String>()
    val re = MutableLiveData<Resource<TrackSearchResult>>()

    val results: LiveData<Resource<TrackSearchResult>> = Transformations
    .switchMap(_query) { search ->
        if (search.isNullOrBlank()) {
            AbsentLiveData.create()
        } else {
            liveData(Dispatchers.IO) {
                val results = searchTrackUseCase.execute(search)
                    emit(results)

            }
        }
    }


    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        _query.value = input
    }

}
package com.rsoumail.upskilling.domain.interactor

import com.rsoumail.upskilling.common.Resource
import com.rsoumail.upskilling.data.local.TrackSearchResult
import com.rsoumail.upskilling.domain.repositoty.TrackRepository

class SearchTrackUseCase(private val trackRepository: TrackRepository){
    suspend fun execute(term: String): Resource<TrackSearchResult> {
        return trackRepository.search(term)
    }
}

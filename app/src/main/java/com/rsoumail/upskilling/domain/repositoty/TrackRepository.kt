package com.rsoumail.upskilling.domain.repositoty

import com.rsoumail.upskilling.common.Resource
import com.rsoumail.upskilling.data.local.TrackSearchResult

interface TrackRepository {
    suspend fun search(term: String) : Resource<TrackSearchResult>
}
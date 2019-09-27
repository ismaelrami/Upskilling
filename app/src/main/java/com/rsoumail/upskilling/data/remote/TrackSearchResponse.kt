package com.rsoumail.upskilling.data.remote

import com.rsoumail.upskilling.domain.entity.Track

data class TrackSearchResponse(val count: Int, val results: List<Track>)
package com.rsoumail.upskilling.data.local

import androidx.room.Entity
import androidx.room.TypeConverters
import com.rsoumail.upskilling.domain.entity.Track

@Entity(primaryKeys = ["query"])
@TypeConverters(TrackTypeConverters::class)
data class TrackSearchResult(val query: String, val totalCount: Int, val tracks: List<Track>)
package com.rsoumail.upskilling.domain.entity

import androidx.room.Entity

@Entity
data class Track(val trackName : String, val artistName : String, val artworkUrl100 : String)
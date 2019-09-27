package com.rsoumail.upskilling.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rsoumail.upskilling.domain.entity.Track

object TrackTypeConverters {
    @TypeConverter
    @JvmStatic
    fun trackListToString(tracks: List<Track>?): String {
        return Gson().toJson(tracks)
    }

    @TypeConverter
    @JvmStatic
    fun stringToTrackList(data: String?): List<Track> {
        val listType = object : TypeToken<List<Track>>() {

        }.type
        return Gson().fromJson(data, listType)
    }


}
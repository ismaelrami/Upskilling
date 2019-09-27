package com.rsoumail.upskilling.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Main database description.
 */
@Database(
    entities = [
        TrackSearchResult::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {

    abstract fun trackDao(): TrackDao
}
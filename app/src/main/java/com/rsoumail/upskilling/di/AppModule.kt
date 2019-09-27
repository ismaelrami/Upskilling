package com.rsoumail.upskilling.di

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.rsoumail.upskilling.AppExecutors
import com.rsoumail.upskilling.data.local.AppDb
import com.rsoumail.upskilling.data.local.TrackDao
import com.rsoumail.upskilling.data.remote.TrackService
import com.rsoumail.upskilling.data.repository.TrackRepository
import com.rsoumail.upskilling.ui.track.TrackViewModel
import com.rsoumail.upskilling.util.LiveDataCallAdapterFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module{

    single { TrackRepository(get(), get(), get(), get()) }

    single { provideDb(androidApplication()) }

    single { provideTrackDao(get()) }

    single { createWebService<TrackService>("https://itunes.apple.com/") }

    single { AppExecutors() }

}

val uiModule = module {
    viewModel { TrackViewModel(get()) }
}

fun provideDb(app: Application): AppDb {
    return Room
        .databaseBuilder(app, AppDb::class.java, "track_result.db")
        .build()
}

fun provideTrackDao(db: AppDb): TrackDao {
    return db.trackDao()
}

inline fun <reified T> createWebService(url: String): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
            .setLenient()
            .create()))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()
        .create(T::class.java)
}
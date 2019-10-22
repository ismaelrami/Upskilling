package com.rsoumail.upskilling.di

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.rsoumail.upskilling.AppExecutors
import com.rsoumail.upskilling.data.local.AppDb
import com.rsoumail.upskilling.data.remote.RemoteDataSource
import com.rsoumail.upskilling.data.remote.TrackService
import com.rsoumail.upskilling.data.repository.TrackRepositoryImpl
import com.rsoumail.upskilling.domain.interactor.SearchTrackUseCase
import com.rsoumail.upskilling.domain.repositoty.TrackRepository
import com.rsoumail.upskilling.ui.track.TrackSearchViewModel
import com.rsoumail.upskilling.util.CustomCallAdapterFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module{

    single { RemoteDataSource(get()) }
    single <TrackRepository> { TrackRepositoryImpl(get(), get()) }

    single { provideDb(androidApplication()) }

    single { createWebService<TrackService>("https://itunes.apple.com/") }

    single { AppExecutors() }

}

val uiModule = module {
    viewModel { TrackSearchViewModel(get()) }
}

val domainModule = module {
    factory { SearchTrackUseCase(get()) }
}

fun provideDb(app: Application): AppDb {
    return Room
        .databaseBuilder(app, AppDb::class.java, "track_result.db")
        .build()
}

inline fun <reified T> createWebService(url: String): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
            .setLenient()
            .create()))
        .addCallAdapterFactory(CustomCallAdapterFactory())
        .build()
        .create(T::class.java)
}
package com.rsoumail.upskilling

import android.app.Application
import com.rsoumail.upskilling.di.appModule
import com.rsoumail.upskilling.di.uiModule
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger

class TrackApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, uiModule), logger = AndroidLogger())
    }
}
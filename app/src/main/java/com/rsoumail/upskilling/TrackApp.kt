package com.rsoumail.upskilling

import android.app.Application
import com.rsoumail.upskilling.di.appModule
import com.rsoumail.upskilling.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin

class TrackApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TrackApp)
            modules(listOf(appModule, uiModule))
            AndroidLogger()
        }
    }
}
package com.udacity.trackmysleepquality

import android.app.Application
import timber.log.Timber

class TrackMySleepQualityApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
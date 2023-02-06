package com.udacity.eggtimer

import android.app.Application
import timber.log.Timber

class EggTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
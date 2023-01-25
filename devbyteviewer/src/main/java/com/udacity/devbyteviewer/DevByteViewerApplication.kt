package com.udacity.devbyteviewer

import android.app.Application
import timber.log.Timber

class DevByteViewerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
package com.udacity.devbyteviewer

import android.app.Application
import com.udacity.devbyteviewer.repository.AppContainer
import com.udacity.devbyteviewer.repository.DefaultAppContainer
import timber.log.Timber

class DevByteViewerApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appContainer = DefaultAppContainer(this)
    }
}
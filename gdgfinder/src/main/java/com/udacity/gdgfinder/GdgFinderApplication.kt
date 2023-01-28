package com.udacity.gdgfinder

import android.app.Application
import com.udacity.gdgfinder.repository.AppContainer
import com.udacity.gdgfinder.repository.DefaultAppContainer
import timber.log.Timber

class GdgFinderApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appContainer = DefaultAppContainer()
    }
}
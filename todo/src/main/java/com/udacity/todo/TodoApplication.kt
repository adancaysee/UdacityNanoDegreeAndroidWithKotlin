package com.udacity.todo

import android.app.Application
import timber.log.Timber

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}
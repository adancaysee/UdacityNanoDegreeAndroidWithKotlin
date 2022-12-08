package com.udacity.guesstheword

import android.app.Application
import timber.log.Timber

class GuessTheWordApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}
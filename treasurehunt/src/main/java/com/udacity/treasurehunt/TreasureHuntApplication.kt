package com.udacity.treasurehunt

import android.app.Application
import com.udacity.treasurehunt.util.createGeofenceNotificationChannel

class TreasureHuntApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        createGeofenceNotificationChannel(this)
    }
}
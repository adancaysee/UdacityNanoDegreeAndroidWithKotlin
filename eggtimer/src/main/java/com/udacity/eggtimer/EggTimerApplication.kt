package com.udacity.eggtimer

import android.app.Application
import com.udacity.eggtimer.util.createEggTimerNotificationChannel
import com.udacity.eggtimer.util.getNotificationManager
import timber.log.Timber

class EggTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        createEggTimerNotificationChannel(
            getNotificationManager(this),
            getString(R.string.egg_notification_channel_id),
            getString(R.string.egg_notification_channel_name)
        )
    }
}
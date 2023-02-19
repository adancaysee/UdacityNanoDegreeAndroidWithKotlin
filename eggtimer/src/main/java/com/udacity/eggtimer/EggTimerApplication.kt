package com.udacity.eggtimer

import android.app.Application
import com.udacity.eggtimer.util.createNotificationChannel
import com.udacity.eggtimer.util.getNotificationManager
import timber.log.Timber

class EggTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val manager = getNotificationManager(this)
        createNotificationChannel(
            manager,
            getString(R.string.egg_notification_channel_id),
            getString(R.string.egg_notification_channel_name)
        )

        createNotificationChannel(
            manager,
            getString(R.string.breakfast_notification_channel_id),
            getString(R.string.breakfast_notification_channel_name)
        )

    }
}
package com.udacity.eggtimer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.udacity.eggtimer.R
import com.udacity.eggtimer.util.getNotificationManager
import com.udacity.eggtimer.util.sendNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            getNotificationManager(context).sendNotification(
                context,
                context.getString(R.string.eggs_ready)
            )
        }


    }
}
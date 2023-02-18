package com.udacity.eggtimer.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.text.format.DateUtils
import androidx.core.app.AlarmManagerCompat
import com.udacity.eggtimer.util.cancelAllNotifications
import com.udacity.eggtimer.util.getAlarmManager
import com.udacity.eggtimer.util.getNotificationManager

private const val REQUEST_CODE = 0

class SnoozeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) {
            return
        }

        val triggerTime = SystemClock.elapsedRealtime() + DateUtils.MINUTE_IN_MILLIS

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val notifyIntent = Intent(context, AlarmReceiver::class.java)
        val notifyPendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            notifyIntent,
            flag
        )

        AlarmManagerCompat.setExactAndAllowWhileIdle(
            getAlarmManager(context),
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            notifyPendingIntent
        )

        getNotificationManager(context).cancelAllNotifications()

    }
}
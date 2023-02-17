package com.udacity.eggtimer.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.udacity.eggtimer.MainActivity
import com.udacity.eggtimer.R

/**
 * Pending intent = System will use it to open your app.
 */

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(applicationContext: Context, messageBody: String) {

    //Step1: Create content intent
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }

    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    //System
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        flag
    )

    // Step2: Create notification
    val notification = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.egg_notification_channel_id)
    )
        .setSmallIcon(R.drawable.cooked_egg)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .build()

    //Step3: Send notification
    notify(NOTIFICATION_ID, notification)
}

fun NotificationManager.cancelAllNotifications() {
    cancelAll()
}

fun createEggTimerNotificationChannel(
    notificationManager: NotificationManager,
    channelId: String,
    channelName: String
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "Time for breakfast"

        notificationManager.createNotificationChannel(notificationChannel)
    }
}


fun hasNotificationPermission(applicationContext: Context) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }


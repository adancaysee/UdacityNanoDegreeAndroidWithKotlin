package com.udacity.eggtimer.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.udacity.eggtimer.MainActivity
import com.udacity.eggtimer.R
import com.udacity.eggtimer.receiver.SnoozeReceiver

/**
 * Pending intent = System will use it to open your app.
 */

private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 1

fun NotificationManager.sendNotification(applicationContext: Context, messageBody: String) {

    //Step1: Create content intent
    val contentPendingIntent = createContentPendingIntent(applicationContext)

    //Step2: Create an action
    val snoozePendingIntent = createSnoozePendingIntent(applicationContext)

    //Step3: Create a style
    val eggImageBitmap = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.cooked_egg
    )
    val bigPictureStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(eggImageBitmap)
        .bigLargeIcon(null) // when expanded big picture for notification is gone


    // Step4: Create notification
    val notification = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.egg_notification_channel_id)
    )
        .setSmallIcon(R.drawable.cooked_egg)
        .setLargeIcon(eggImageBitmap)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setStyle(bigPictureStyle)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(
            R.drawable.egg_icon,
            applicationContext.getString(R.string.snooze),
            snoozePendingIntent
        )
        .build()

    //Step5: Send notification
    notify(NOTIFICATION_ID, notification)
}


private fun createContentPendingIntent(applicationContext: Context): PendingIntent {
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    return PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        flag
    )
}

private fun createSnoozePendingIntent(applicationContext: Context): PendingIntent {
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
    } else {
        PendingIntent.FLAG_ONE_SHOT
    }
    val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)
    return PendingIntent.getBroadcast(
        applicationContext,
        REQUEST_CODE,
        snoozeIntent,
        flag
    )
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
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            description = "Time for breakfast"
            setShowBadge(false)
        }

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


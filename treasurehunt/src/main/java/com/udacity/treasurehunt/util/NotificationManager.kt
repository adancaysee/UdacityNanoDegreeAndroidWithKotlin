package com.udacity.treasurehunt.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.udacity.treasurehunt.ui.MainActivity
import com.udacity.treasurehunt.R


private const val NOTIFICATION_ID = 33
private const val CHANNEL_ID = "GeofenceChannel"


fun NotificationManager.sendGeofenceEnteredNotification(context: Context, foundIndex: Int) {

    val flag = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    val contentIntent = Intent(context, MainActivity::class.java)
    contentIntent.putExtra(GeofencingConstants.EXTRA_GEOFENCE_INDEX, foundIndex)
    val pendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_ID,
        contentIntent,
        flag
    )

    val icon = BitmapFactory.decodeResource(context.resources, R.drawable.map_small)

    val bigPictureStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(icon)
        .bigLargeIcon(null)

    val notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
        setContentTitle(context.getString(R.string.app_name))
        setContentText(
            context.getString(
                R.string.content_text,
                context.getString(GeofencingConstants.LANDMARK_DATA[foundIndex].name)
            )
        )
        setSmallIcon(R.drawable.map_small)
        setStyle(bigPictureStyle)
        setLargeIcon(icon)
        setContentIntent(pendingIntent)
        priority = NotificationManagerCompat.IMPORTANCE_HIGH
    }.build()

    notify(NOTIFICATION_ID, notification)

}

fun createGeofenceNotificationChannel(context: Context) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            setShowBadge(false)
            description = context.getString(R.string.notification_channel_description)
        }
        getNotificationManager(context).createNotificationChannel(channel)
    }
}

fun getNotificationManager(context: Context): NotificationManager =
    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
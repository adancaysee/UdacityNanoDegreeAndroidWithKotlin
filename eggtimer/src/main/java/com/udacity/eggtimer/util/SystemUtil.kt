package com.udacity.eggtimer.util

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences

fun getPreference(application: Application): SharedPreferences =
    application.getSharedPreferences(
        "com.example.android.eggtimer",
        Context.MODE_PRIVATE
    )

fun getAlarmManager(application: Application): AlarmManager =
    application.getSystemService(Context.ALARM_SERVICE) as AlarmManager

fun getNotificationManager(context: Context): NotificationManager =
    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



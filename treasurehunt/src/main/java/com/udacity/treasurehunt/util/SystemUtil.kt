package com.udacity.treasurehunt.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.hasPermission(permission: String) =
    ContextCompat.checkSelfPermission(
        this, permission
    ) == PackageManager.PERMISSION_GRANTED

fun getPreference(application: Application): SharedPreferences =
    application.getSharedPreferences(
        "com.udacity.treasurehunt",
        Context.MODE_PRIVATE
    )
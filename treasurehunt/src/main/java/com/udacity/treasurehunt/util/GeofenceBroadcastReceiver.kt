package com.udacity.treasurehunt.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.udacity.treasurehunt.R
import com.udacity.treasurehunt.ui.ACTION_GEOFENCE_EVENT
import timber.log.Timber

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) {
            return
        }
        if (intent.action == ACTION_GEOFENCE_EVENT) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            if (geofencingEvent == null || geofencingEvent.hasError()) {
                Timber.e(errorMessage(context, geofencingEvent?.errorCode))
                // Timber.e(GeofenceStatusCodes.getStatusCodeString(geofencingEvent?.errorCode ?: GeofenceStatusCodes.ERROR))
                return
            }

            if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                Timber.e(context.resources.getString(R.string.geofence_entered))

                val geofenceId = when {
                    !geofencingEvent.triggeringGeofences.isNullOrEmpty() -> {
                        geofencingEvent.triggeringGeofences!![0].requestId
                    }
                    else -> {
                        Timber.e("No Geofence Trigger Found! Abort mission!")
                        return
                    }
                }
                val foundIndex = GeofencingConstants.LANDMARK_DATA.indexOfFirst {
                    it.id == geofenceId
                }

                if (-1 == foundIndex) {
                    Timber.e("Unknown Geofence: Abort Mission")
                    return
                }

                getNotificationManager(context).sendGeofenceEnteredNotification(context, foundIndex)
            }


        }

    }
}
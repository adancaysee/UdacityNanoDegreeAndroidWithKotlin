package com.udacity.treasurehunt.ui

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.lifecycle.*
import com.google.android.gms.location.*
import com.udacity.treasurehunt.util.GeofenceBroadcastReceiver
import com.udacity.treasurehunt.util.GeofencingConstants

import com.udacity.treasurehunt.R

/**
Location Services provides
1. Setting Client --> For settings about location
2. FusedLocationProvider --> Location track and user's lastKnownLocation
3. Geofencing --> User's current location + User's proximity to locations that may be of interest.
 */

private const val GEOFENCE_REQUEST_CODE = 0
const val ACTION_GEOFENCE_EVENT = "treasureHunt.action.ACTION_GEOFENCE_EVENT"
private const val HINT_INDEX_KEY = "hintIndex"
private const val GEOFENCE_INDEX_KEY = "geofenceIndex"

class GeofenceViewModel(private val application: Application, state: SavedStateHandle) :
    AndroidViewModel(application) {

    private var geofencePendingIntent: PendingIntent

    private var geofencingClient: GeofencingClient

    private val geofenceIndex = state.getLiveData(GEOFENCE_INDEX_KEY, -1)
    private val hintIndex = state.getLiveData(HINT_INDEX_KEY, 0)



    val geofenceHintResourceId = Transformations.map(geofenceIndex) {
        val index = geofenceIndex.value ?: -1
        when {
            index < 0 -> R.string.not_started_hint
            index < GeofencingConstants.NUM_LANDMARKS -> GeofencingConstants.LANDMARK_DATA[geofenceIndex.value!!].hint
            else -> R.string.geofence_over
        }
    }

    val geofenceImageResourceId = Transformations.map(geofenceIndex) {
        val index = geofenceIndex.value ?: -1
        when {
            index < GeofencingConstants.NUM_LANDMARKS -> R.drawable.android_map
            else -> R.drawable.android_treasure
        }
    }

    private val _checkLocationSettingFailureEvent = MutableLiveData<Exception?>()
    val checkLocationSettingFailureEvent: LiveData<Exception?>
        get() = _checkLocationSettingFailureEvent

    private val _removeAllGeofencesEvent = MutableLiveData<Boolean?>()
    val removeAllGeofencesEvent: LiveData<Boolean?>
        get() = _removeAllGeofencesEvent

    private val _addGeofenceForClueEvent = MutableLiveData<Boolean?>()
    val addGeofenceForClueEvent: LiveData<Boolean?>
        get() = _addGeofenceForClueEvent

    init {
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        }
        val intent = Intent(application, GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        geofencePendingIntent = PendingIntent.getBroadcast(
            application,
            GEOFENCE_REQUEST_CODE,
            intent,
            flag
        )

        geofencingClient = LocationServices.getGeofencingClient(application)
    }

    fun checkDeviceLocationSettingsAndStartGeofence() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_LOW_POWER, 10000)
            .build()

        val locationSettingRequest =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
                .build()

        val settingClient = LocationServices.getSettingsClient(application)
        val locationSettingResponseTask =
            settingClient.checkLocationSettings(locationSettingRequest)

        locationSettingResponseTask.addOnCompleteListener {
            if (it.isSuccessful) addGeofenceForClue()
        }.addOnFailureListener { exception ->
            _checkLocationSettingFailureEvent.value = exception

        }
    }

    fun doneCheckLocationFailureEvent() {
        _checkLocationSettingFailureEvent.value = null
    }

    @SuppressLint("MissingPermission")
    fun addGeofenceForClue() {
        if (geofenceIsActive()) return
        val currentGeofenceIndex = nextGeofenceIndex()
        if (currentGeofenceIndex >= GeofencingConstants.NUM_LANDMARKS) {
            removeGeofences()
            geofenceActivated()
            return
        }
        val landmarkDataObject = GeofencingConstants.LANDMARK_DATA[currentGeofenceIndex]
        val geofence = Geofence.Builder().apply {
            setRequestId(landmarkDataObject.id)
            setCircularRegion(
                landmarkDataObject.latLong.latitude,
                landmarkDataObject.latLong.longitude,
                GeofencingConstants.GEOFENCE_RADIUS_IN_METERS
            )
            setExpirationDuration(GeofencingConstants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
            setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
        }.build()

        val geofenceRequest = GeofencingRequest.Builder().apply {
            addGeofence(geofence)
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        }.build()

        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnCompleteListener {
                geofencingClient.addGeofences(geofenceRequest, geofencePendingIntent).run {
                    addOnCompleteListener {
                        _addGeofenceForClueEvent.value = it.isSuccessful
                    }
                }
            }
        }
    }

    fun removeGeofences() {
        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnSuccessListener {
                _removeAllGeofencesEvent.value = true
            }
            addOnFailureListener {
                _removeAllGeofencesEvent.value = false
            }
        }
    }

    fun doneAddGeofenceForClueEvent() {
        _addGeofenceForClueEvent.value = null
    }

    fun doneRemoveAllGeofencesEvent() {
        _removeAllGeofencesEvent.value = null
    }

    fun updateHint(currentIndex: Int) {
        hintIndex.value = currentIndex + 1

    }

    private fun geofenceActivated() {
        geofenceIndex.value = hintIndex.value

    }



    private fun geofenceIsActive() = geofenceIndex.value == hintIndex.value

    private fun nextGeofenceIndex() = hintIndex.value ?: 0

}
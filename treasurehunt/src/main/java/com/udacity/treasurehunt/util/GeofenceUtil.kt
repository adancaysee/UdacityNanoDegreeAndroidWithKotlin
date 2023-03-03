package com.udacity.treasurehunt.util

import com.google.android.gms.maps.model.LatLng
import com.udacity.treasurehunt.R
import java.util.concurrent.TimeUnit

data class LandmarkDataObject(val id: String, val hint: Int, val name: Int, val latLong: LatLng)


internal object GeofencingConstants {

    /**
     * Used to set an expiration time for a geofence. After this amount of time, Location services
     * stops tracking the geofence. For this sample, geofences expire after one hour.
     */
    val GEOFENCE_EXPIRATION_IN_MILLISECONDS: Long = TimeUnit.HOURS.toMillis(1)

    val LANDMARK_DATA = arrayOf(
        LandmarkDataObject(
            "golden_gate_bridge",
            R.string.golden_gate_bridge_hint,
            R.string.golden_gate_bridge_location,
            LatLng(37.819927, -122.478256)),

        LandmarkDataObject(
            "ferry_building",
            R.string.ferry_building_hint,
            R.string.ferry_building_location,
            LatLng(37.795490, -122.394276)),

        LandmarkDataObject(
            "pier_39",
            R.string.pier_39_hint,
            R.string.pier_39_location,
            LatLng(37.808674, -122.409821)),

        LandmarkDataObject(
            "union_square",
            R.string.union_square_hint,
            R.string.union_square_location,
            LatLng(37.788151, -122.407570))
    )

    val NUM_LANDMARKS = LANDMARK_DATA.size
    const val GEOFENCE_RADIUS_IN_METERS = 100f
    const val EXTRA_GEOFENCE_INDEX = "GEOFENCE_INDEX"
}
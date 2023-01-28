package com.udacity.gdgfinder.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GdgChapter(
    val name: String,
    val city: String,
    val country: String,
    val region: String,
    val website: String,
    val geo: LatLong
) : Parcelable

@Parcelize
data class LatLong(
    val lat: Double,
    val long: Double
) : Parcelable
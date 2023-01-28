package com.udacity.gdgfinder.network

import com.squareup.moshi.Json
import com.udacity.gdgfinder.domain.GdgChapter
import com.udacity.gdgfinder.domain.LatLong

data class NetworkGdgChapter(
    @Json(name = "chapter_name") val name: String,
    @Json(name = "cityarea") val city: String,
    val country: String,
    val region: String,
    val website: String,
    val geo: NetworkLatLong
)

data class NetworkLatLong(
    val lat: Double,
    @Json(name = "lng")
    val long: Double
)

data class GdgResponse(
    @Json(name = "filters_") val filters: Filter,
    @Json(name = "data") val chapters: List<NetworkGdgChapter>
)

data class Filter(
    @Json(name = "region") val regions: List<String>
)


fun List<NetworkGdgChapter>.asDomain(): List<GdgChapter> {
    return map {
        GdgChapter(
            name = it.name,
            city = it.city,
            country = it.country,
            region = it.region,
            website = it.website,
            geo = it.geo.asDomain()
        )
    }
}

fun NetworkLatLong.asDomain(): LatLong {
    return LatLong(
        lat = this.lat,
        long = this.long
    )
}
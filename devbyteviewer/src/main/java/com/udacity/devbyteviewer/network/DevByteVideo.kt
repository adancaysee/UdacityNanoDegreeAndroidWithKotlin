package com.udacity.devbyteviewer.network

import com.udacity.devbyteviewer.database.DevByteVideoEntity

data class DevByteVideo(
    val title: String,
    val description: String,
    val url: String,
    val updated: String,
    val thumbnail: String,
    val closedCaptions: String?
)

data class DevByteVideoContainer(
    val videos: List<DevByteVideo>
)

fun List<DevByteVideo>.asDatabaseModel() : List<DevByteVideoEntity> {
    return map {
        DevByteVideoEntity(
            url = it.url,
            updated = it.updated,
            title = it.title,
            description = it.description,
            thumbnail = it.thumbnail,
        )
    }
}


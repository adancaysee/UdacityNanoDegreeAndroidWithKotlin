package com.udacity.devbyteviewer.network

import com.udacity.devbyteviewer.database.EntityDevByteVideo


data class NetworkDevByteVideo(
    val title: String,
    val description: String,
    val url: String,
    val updated: String,
    val thumbnail: String,
    val closedCaptions: String?
)

data class NetworkDevByteVideoContainer(
    val videos: List<NetworkDevByteVideo>
)

fun List<NetworkDevByteVideo>.asDatabaseModel() : List<EntityDevByteVideo> {
    return map {
        EntityDevByteVideo(
            url = it.url,
            updated = it.updated,
            title = it.title,
            description = it.description,
            thumbnail = it.thumbnail,
        )
    }
}


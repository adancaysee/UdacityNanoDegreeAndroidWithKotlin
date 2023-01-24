package com.udacity.devbyteviewer.network

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

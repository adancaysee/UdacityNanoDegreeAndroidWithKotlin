package com.udacity.devbyteviewer.repository

import androidx.lifecycle.LiveData
import com.udacity.devbyteviewer.domain.DevByteVideo

interface VideosRepository {
    val videos: LiveData<List<DevByteVideo>>
    suspend fun refreshVideos()
}
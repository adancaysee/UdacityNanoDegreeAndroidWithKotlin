package com.udacity.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.devbyteviewer.database.LocalVideosDataSource
import com.udacity.devbyteviewer.database.asDomainModel
import com.udacity.devbyteviewer.domain.DevByteVideo
import com.udacity.devbyteviewer.network.RemoteVideosDataSource
import com.udacity.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class DevByteApiStatus {
    object Loading : DevByteApiStatus()
    object Success : DevByteApiStatus()
    data class Failure(val errorMsg: String) : DevByteApiStatus()
}

class DefaultVideosRepository(
    private val localVideosDataSource: LocalVideosDataSource,
    private val remoteVideosDataSource: RemoteVideosDataSource,
) : VideosRepository {

    override val videos: LiveData<List<DevByteVideo>>
        get() {
            return Transformations.map(localVideosDataSource.getPlayList()) {
                it.asDomainModel()
            }
        }

    override suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val list = remoteVideosDataSource.getPlayList().videos
            localVideosDataSource.insertPlayList(list.asDatabaseModel())
        }
    }


}
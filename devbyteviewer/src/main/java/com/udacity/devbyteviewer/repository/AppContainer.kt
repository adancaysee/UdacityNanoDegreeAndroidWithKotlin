package com.udacity.devbyteviewer.repository

import android.content.Context
import com.udacity.devbyteviewer.database.DevByteDatabase
import com.udacity.devbyteviewer.network.DevByteApi

interface AppContainer {
    val videosRepository: VideosRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

    private val localVideosDataSource = DevByteDatabase.getInstance(context).dao

    override val videosRepository: VideosRepository by lazy {
        DefaultVideosRepository(localVideosDataSource, DevByteApi.remoteVideosDataSource)
    }

}
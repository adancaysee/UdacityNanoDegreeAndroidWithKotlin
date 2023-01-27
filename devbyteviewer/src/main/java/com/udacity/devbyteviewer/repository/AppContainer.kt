package com.udacity.devbyteviewer.repository

import android.content.Context
import com.udacity.devbyteviewer.database.DevByteDatabase
import com.udacity.devbyteviewer.network.RemoteVideosDataSource
import com.udacity.devbyteviewer.network.getRetrofit

interface AppContainer {
    val videosRepository: VideosRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

    private val remoteVideosDataSource: RemoteVideosDataSource by lazy {
        getRetrofit().create(RemoteVideosDataSource::class.java)
    }
    private val localVideosDataSource = DevByteDatabase.getInstance(context).dao

    override val videosRepository: VideosRepository by lazy {
        DefaultVideosRepository(localVideosDataSource, remoteVideosDataSource)
    }

}
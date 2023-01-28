package com.udacity.gdgfinder.repository

import com.udacity.gdgfinder.network.RemoteGdgChapterDataSource
import com.udacity.gdgfinder.network.getRetrofit

interface AppContainer {
    val gdgChapterRepository: GdgChapterRepository
}

class DefaultAppContainer : AppContainer {

    private val remoteGdgChapterDataSource: RemoteGdgChapterDataSource by lazy {
        getRetrofit().create(RemoteGdgChapterDataSource::class.java)
    }

    override val gdgChapterRepository: GdgChapterRepository by lazy {
        DefaultGdgChapterRepository(remoteGdgChapterDataSource)
    }
}
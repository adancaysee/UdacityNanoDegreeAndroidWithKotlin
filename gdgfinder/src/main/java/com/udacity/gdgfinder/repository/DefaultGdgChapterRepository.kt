package com.udacity.gdgfinder.repository

import com.udacity.gdgfinder.domain.GdgChapter
import com.udacity.gdgfinder.network.RemoteGdgChapterDataSource
import com.udacity.gdgfinder.network.asDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultGdgChapterRepository(
    private val remoteGdgChapterDataSource: RemoteGdgChapterDataSource
) : GdgChapterRepository {

    override suspend fun getChapters(): List<GdgChapter> {
        val list = withContext(Dispatchers.IO) {
            val response = remoteGdgChapterDataSource.getChapters()
            response.chapters.asDomain()
        }
        return list
    }
}
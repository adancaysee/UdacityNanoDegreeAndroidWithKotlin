package com.udacity.gdgfinder.network

import retrofit2.http.GET

interface RemoteGdgChapterDataSource {
    @GET("gdg-directory.json")
    suspend fun getChapters(): GdgResponse
}
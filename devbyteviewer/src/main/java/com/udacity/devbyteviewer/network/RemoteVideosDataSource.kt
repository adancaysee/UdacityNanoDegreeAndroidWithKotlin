package com.udacity.devbyteviewer.network

import retrofit2.http.GET

interface RemoteVideosDataSource {
    @GET("devbytes.json")
    suspend fun getPlayList(): NetworkDevByteVideoContainer
}

/**
 * If I don't use suspend keyword for getPlayList, an exception is thrown;
 * java.lang.IllegalArgumentException: Unable to create call adapter for class
 *
 * Previously it's used (Now it's not necessary)
 *  .addCallAdapterFactory(CoroutineCallAdapterFactory())
 *
 *  We can change retrofit call to coroutine with suspend
 */
package com.udacity.devbyteviewer.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://devbytes.udacity.com/"

sealed class DevByteApiStatus {
    object Loading : DevByteApiStatus()
    data class Success(val list: List<DevByteVideo>) : DevByteApiStatus()
    data class Failure(val errorMsg: String) : DevByteApiStatus()
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()


interface DevByteVideosService {
    @GET("devbytes.json")
    suspend fun getPlayList(): DevByteVideoContainer
}

object RemoteVideosDataSource {
    val service: DevByteVideosService by lazy { retrofit.create(DevByteVideosService::class.java) }
}
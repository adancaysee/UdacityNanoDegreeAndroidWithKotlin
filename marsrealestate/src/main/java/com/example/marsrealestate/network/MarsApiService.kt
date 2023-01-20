package com.example.marsrealestate.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


/**
 * Retrofit run our request on the background with callback default.
 * ( Call<String> ).enqueue() --> start network request on a background thread
 * Retrofit need at least two thing
 *  -- BASE URL
 *  -- Converter
 *
 * ScalarsConverterFactory is used for convert response to string
 *  Response --> String
 *
 */

private const val BASE_URL = "https://mars.udacity.com/"


/**
 * For kotlin adapter support -- Moshi's annotations to work properly with Kotlin
 * If I don't use -- I get an exception
 * Reflective serialization of Kotlin classes without using kotlin-reflect has undefined
 * and unexpected behavior
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()


interface MarsApiService {
    @GET("realestate")
    fun getRealEstates(): Call<List<MarsProperty>>
}

object MarsRetrofitClient {
    //Retrofit create call is expensive. So we only get service once
    val marsApiService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

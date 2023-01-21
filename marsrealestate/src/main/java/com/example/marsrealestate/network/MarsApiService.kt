package com.example.marsrealestate.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


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

enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

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
    suspend fun getRealEstates(@Query("filter") type:String): List<MarsProperty>
}

object MarsRetrofitClient {
    //Retrofit create call is expensive. So we only get service once
    val marsApiService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

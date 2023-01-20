package com.example.marsrealestate.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
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

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()


interface MarsApiService {
    @GET("realestate")
    fun getRealEstates(): Call<String>
}

object MarsRetrofitClient {
    //Retrofit create call is expensive. So we only get service once
    val marsApiService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

package com.example.vsuet.API

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://vsuet.app/api/"
private const val ENDPOINT = "rating/get"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RatingApiService {
    @GET(ENDPOINT)
    fun getProperties(@Query("recordBookNum") recordNumBook: String):
            Call<RatingProperty>
}

object RatingApi {
    val retrofitService: RatingApiService by lazy {
        retrofit.create(RatingApiService::class.java)
    }
}
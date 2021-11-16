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
private const val ENDPOINT = "schedule/get/all"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WallApiService {
    @GET(ENDPOINT)
    fun getProperties(@Query("group") groupId: String, @Query("time") time: String):
            Call<ScheduleProperty>
}

object WallApi {
    val retrofitService: WallApiService by lazy {
        retrofit.create(WallApiService::class.java)
    }
}

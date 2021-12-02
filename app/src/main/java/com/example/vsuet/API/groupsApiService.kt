package com.example.vsuet.API

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://vsuet.app/api/"
private const val ENDPOINT = "group/get/all"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GroupsApiService {
    @GET(ENDPOINT)
    fun getProperties():
            Call<GroupProperty>
}

object GroupsApi {
    val retrofitService: GroupsApiService by lazy {
        retrofit.create(GroupsApiService::class.java)
    }
}
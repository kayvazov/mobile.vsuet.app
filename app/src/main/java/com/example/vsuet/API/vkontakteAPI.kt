package com.example.vsuet.API

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://api.vk.com/method/"
private const val ENDPOINT =
    "wall.get?access_token=2d1ee64d2d1ee64d2d1ee64db92d673f5022d1e2d1ee64d4c669f6321621363b2c71825&v=5.131&domain=vsuet_official"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @GET(ENDPOINT)
    fun getProperties():
            Call<NewsProperty>
}

object VkontakteApi {
    val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}

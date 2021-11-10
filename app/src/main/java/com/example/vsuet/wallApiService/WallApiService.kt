package com.example.vsuet.wallApiService

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL =
    "https://api.vk.com/method/"
private const val ENDPOINT = "wall.get?access_token=2d1ee64d2d1ee64d2d1ee64db92d673f5022d1e2d1ee64d4c669f6321621363b2c71825&v=5.131&domain=vsuet_official"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    interface WallApiService {
        @GET(ENDPOINT)
        fun getProperties():
                Call<String>
    }

    object WallApi {
        val retrofitService: WallApiService by lazy {
            retrofit.create(WallApiService::class.java)
        }
    }


package com.example.vsuet.API

import com.squareup.moshi.Json

data class OrderProperty(
    @Json(name = "data") val data: List<Order>
)

data class Order(
    @Json(name = "_id") val _id: String,
    @Json(name = "isReady") val isReady: Boolean,
    @Json(name = "createdTime") val createdTime: String
)
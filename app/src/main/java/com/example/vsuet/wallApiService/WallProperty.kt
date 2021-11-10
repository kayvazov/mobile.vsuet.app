package com.example.vsuet.wallApiService

data class WallProperty(
    val id: Int,
    val from_id: Int,
    val owner_id: Int,
    val date: Int,
    val marked_as_ads: Int,
    val post_type: String,
    val text: String,
    val is_pinned: Int
)

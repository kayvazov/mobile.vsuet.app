package com.example.vsuet.API

import com.squareup.moshi.Json

data class RecordProperty(
    @Json(name = "status") val status: Boolean,
    @Json(name = "data") val data: StudentCard?
)

data class StudentCard(
    @Json(name = "groups") val groups: List<StudentGroup>,
    @Json(name = "createdTime") val createdTime: String,
    @Json(name = "_id") val _id: String,
    @Json(name = "recordBookNum") val recordBookNum: String,
    @Json(name = "__v") val __v: Int,
    @Json(name = "faculty") val faculty: StudentFaculty,
    @Json(name = "ratingUpdatedAt") val ratingUpdatedAt: String
)

data class StudentGroup(
    @Json(name = "createdTime") val createdTime: String,
    @Json(name = "_id") val _id: String,
    @Json(name = "faculty") val faculty: String,
    @Json(name = "name") val name: String,
    @Json(name = "value") val value: Int,
    @Json(name = "__v") val __v: Int
)

data class StudentFaculty(
    @Json(name = "createdTime") val createdTime: String,
    @Json(name = "_id") val _id: String,
    @Json(name = "name") val name: String,
    @Json(name = "value") val value: Int,
    @Json(name = "__v") val __v: Int
)
package com.example.vsuet.API

import com.squareup.moshi.Json

data class TeacherLessonProperty(
    @Json(name = "status") val status: Int,
    @Json(name = "data") val data: List<TeacherLesson>
)

data class TeacherLesson(
    @Json(name = "_id") val _id: String,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: String,
    @Json(name = "teacher") val teacher: String,
    @Json(name = "audience") val audience: String,
    @Json(name = "time") val time: LessonTime,
    @Json(name = "day") val day: String,
    @Json(name = "group") val group: String,
    @Json(name = "subgroup") val subgroup: Int,
    @Json(name = "weekType") val weekType: Boolean,
    @Json(name = "__v") val __v: Int,
    @Json(name = "subgroups") val subgroups: List<Int>? = null,
    @Json(name = "groups") val groups: List<String>? = null
)
package com.example.vsuet.API

import com.squareup.moshi.Json

data class ScheduleProperty(
    @field:Json(name = "status") val status: Int,
    @field:Json(name = "data") val data: FullLessonsData
)

data class FullLessonsData(@field:Json(name = "lessons") val lessons: List<LessonProperty>, @field:Json(name = "updatedTime") val updatedTime: String)
data class GroupLessons(@field:Json(name = "lessons") val lessons: List<LessonProperty>)
data class LessonProperty(
    @field:Json(name = "time") val time: LessonTime,
    @field:Json(name = "_id") val _id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "teacher") val teacher: String,
    @field:Json(name = "audience") val audience: String,
    @field:Json(name = "day") val day: String,
    @field:Json(name = "group") val group: String,
    @field:Json(name = "subgroup") val subgroup: Int,
    @field:Json(name = "weekType") val weekType: Boolean,
    @field:Json(name = "__v") val __v: Int
)

data class LessonTime(
    @field:Json(name = "start") val start: String,
    @field:Json(name = "end") val end: String
)

data class UpdatedTime(
    @field:Json(name = "updatedTime") val updatedTime: String
)

data class Id(
    @field:Json(name = "_id") val _id: String
)

data class Name(
    @field:Json(name = "name") val name: String
)

data class Type(
    @field:Json(name = "type") val type: String
)

data class Teacher(
    @field:Json(name = "teacher") val teacher: String
)

data class Audience(
    @field:Json(name = "audience") val audience: String
)

data class Day(
    @field:Json(name = "day") val day: String
)

data class Group(
    @field:Json(name = "group") val group: String
)

data class Subgroup(
    @field:Json(name = "subgroup") val subgroup: String
)

data class WeekType(
    @field:Json(name = "weekType") val weekType: Boolean
)

data class Unknown(
    @field:Json(name = "__v") val __v: Int
)

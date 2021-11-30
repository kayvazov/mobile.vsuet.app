package com.example.vsuet.API

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class ScheduleProperty(
    @field:Json(name = "status") val status: Int,
    @field:Json(name = "data") val data: FullLessonsData
)

data class FullLessonsData(
    @field:Json(name = "lessons") val lessons: List<LessonProperty>,
    @field:Json(name = "updatedTime") val updatedTime: String
)

@Entity
data class LessonProperty(
    @PrimaryKey @ColumnInfo(name = "_id") @field:Json(name = "_id") val _id: String,
    @ColumnInfo(name = "time") @field:Json(name = "time") val time: LessonTime,
    @ColumnInfo(name = "name") @field:Json(name = "name") val name: String,
    @ColumnInfo(name = "type") @field:Json(name = "type") val type: String,
    @ColumnInfo(name = "teacher") @field:Json(name = "teacher") val teacher: String,
    @ColumnInfo(name = "audience") @field:Json(name = "audience") val audience: String,
    @ColumnInfo(name = "day") @field:Json(name = "day") val day: String,
    @ColumnInfo(name = "group") @field:Json(name = "group") val group: String,
    @ColumnInfo(name = "subgroup") @field:Json(name = "subgroup") val subgroup: Int,
    @ColumnInfo(name = "weekType") @field:Json(name = "weekType") val weekType: Boolean,
    @ColumnInfo(name = "__v") @field:Json(name = "__v") val __v: Int
)

data class LessonTime(
    @field:Json(name = "start") val start: String,
    @field:Json(name = "end") val end: String
)


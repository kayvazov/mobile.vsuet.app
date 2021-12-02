package com.example.vsuet.API

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class TeacherLessonProperty(
    @Json(name = "status") val status: Int,
    @Json(name = "data") val data: List<TeacherLesson>
)

@Entity
data class TeacherLesson(
    @PrimaryKey(autoGenerate = false) @Json(name = "_id") val _id: String,
    @ColumnInfo(name = "name") @Json(name = "name") val name: String,
    @ColumnInfo(name = "type") @Json(name = "type") val type: String,
    @ColumnInfo(name = "teacher") @Json(name = "teacher") val teacher: String,
    @ColumnInfo(name = "audience") @Json(name = "audience") val audience: String,
    @ColumnInfo(name = "time") @Json(name = "time") val time: LessonTime,
    @ColumnInfo(name = "day") @Json(name = "day") val day: String,
    @ColumnInfo(name = "group") @Json(name = "group") val group: String,
    @ColumnInfo(name = "subgroup") @Json(name = "subgroup") val subgroup: Int,
    @ColumnInfo(name = "weekType") @Json(name = "weekType") val weekType: Boolean,
    @ColumnInfo(name = "__v") @Json(name = "__v") val __v: Int,
    @ColumnInfo(name = "subgroups") @Json(name = "subgroups") val subgroups: List<Int>? = null,
    @ColumnInfo(name = "groups") @Json(name = "groups") val groups: List<String>? = null
)
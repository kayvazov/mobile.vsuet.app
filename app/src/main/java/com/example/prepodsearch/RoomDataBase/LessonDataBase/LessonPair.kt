package com.example.prepodsearch.RoomDataBase.LessonDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonPair(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "lessonName") val lessonName: String,
    @ColumnInfo(name = "lessonTime") val lessonTime: String,
    @ColumnInfo(name = "lessonClass") val lessonClass: Int,
    @ColumnInfo(name = "lessonTeacher") val lessonTeacher: String,
    @ColumnInfo(name = "lessonDay") val lessonDay: String,
    @ColumnInfo(name = "numerator") val numerator: Boolean
)

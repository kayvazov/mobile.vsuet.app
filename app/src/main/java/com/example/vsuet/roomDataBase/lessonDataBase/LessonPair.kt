package com.example.vsuet.roomDataBase.lessonDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonPair(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "lessonName") val lessonName: String,
    @ColumnInfo(name = "lessonTime") val lessonTime: String,
    @ColumnInfo(name = "lessonClass") val lessonClass: String,
    @ColumnInfo(name = "lessonTeacher") val lessonTeacher: String,
    @ColumnInfo(name = "lessonDay") val lessonDay: String,
    @ColumnInfo(name = "podGroup") val podGroup: Int,
    @ColumnInfo(name = "groupName") val groupName: String,
    @ColumnInfo(name = "numerator") val numerator: Int
)

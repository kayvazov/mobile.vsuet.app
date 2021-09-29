package com.example.prepodsearch.RoomDataBase.TeacherDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Teacher(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "teacherName") val teacherName: String,
    @ColumnInfo(name = "teacherFaculty") val teacherFaculty: String,
)

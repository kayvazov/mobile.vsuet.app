package com.example.prepodsearch.roomDataBase.teacherDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Teacher(
    @ColumnInfo(name = "teacherName") val teacherName: String?,
    @ColumnInfo(name = "teacherFaculty") val teacherFaculty: String?,
    @PrimaryKey(autoGenerate = true) val id: Long?
)

package com.example.vsuet.API

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class TeacherProperty(
    @PrimaryKey(autoGenerate = true) val id: Long = 1L,
    @ColumnInfo(name = "status")@Json(name = "status") val status: Int,
    @ColumnInfo(name = "data")@Json(name = "data") val data: List<String>
)


package com.example.vsuet.API

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class GroupProperty(
    @field:Json(name = "status") val status: Boolean,
    @field:Json(name = "data") val data: List<Group>
)

@Entity
data class Group(
    @ColumnInfo(name = "createdTime") @field:Json(name = "createdTime") val createdTime: String,
    @PrimaryKey(autoGenerate = false) @field:Json(name = "_id") val _id: String,
    @ColumnInfo(name = "faculty") @field:Json(name = "faculty") val faculty: String,
    @ColumnInfo(name = "name") @field:Json(name = "name") val name: String,
    @ColumnInfo(name = "value") @field:Json(name = "value") val value: String,
    @ColumnInfo(name = "__V") @field:Json(name = "__v") val __v: Int
)
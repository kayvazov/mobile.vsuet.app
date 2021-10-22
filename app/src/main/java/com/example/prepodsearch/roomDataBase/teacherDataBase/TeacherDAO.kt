package com.example.prepodsearch.roomDataBase.teacherDataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface TeacherDAO {

    @Query("SELECT * FROM teacher")
    fun getAllTeachers() : LiveData<List<Teacher>>


}
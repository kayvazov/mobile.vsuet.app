package com.example.prepodsearch.RoomDataBase.TeacherDataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface TeacherDAO {

    @Query("SELECT * FROM teacher WHERE teacherFaculty like :faculty")
    fun getFacultyTeachers(faculty: String): LiveData<List<Teacher>>

    @Query("SELECT * FROM teacher WHERE teacherName like :teacherName")
    fun getTeacher(teacherName: String): Teacher


}
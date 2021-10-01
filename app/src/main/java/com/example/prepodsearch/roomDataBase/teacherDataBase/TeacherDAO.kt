package com.example.prepodsearch.roomDataBase.teacherDataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface TeacherDAO {

    @Query("SELECT * FROM teacher WHERE teacherFaculty like :faculty")
    fun getFacultyTeachers(faculty: String): LiveData<List<Teacher>>


}
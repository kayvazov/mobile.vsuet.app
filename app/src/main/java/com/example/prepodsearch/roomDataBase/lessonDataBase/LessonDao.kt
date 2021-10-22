package com.example.prepodsearch.roomDataBase.lessonDataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface LessonDao {

    @Query("SELECT * FROM lessonPair WHERE lessonTeacher like :teacherName AND lessonDay like :day")
    fun getTeachersLessons(teacherName: String, day: String) : LiveData<List<LessonPair>>

    @Query("SELECT * FROM lessonPair")
    fun getAllLessons() : LiveData<List<LessonPair>>

}
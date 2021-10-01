package com.example.prepodsearch.roomDataBase.lessonDataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface LessonDao {

    @Query("SELECT * FROM lessonPair where lessonTime like :time AND lessonTeacher like:teacher")
    fun getCurrentLesson(time: String, teacher: String): LiveData<LessonPair>

    @Query("SELECT * FROM lessonPair WHERE lessonTeacher like :teacherName AND lessonDay like :day AND numerator like :numerator")
    fun getTeachersLessons(teacherName: String, day: String, numerator: Boolean) : LiveData<List<LessonPair>>

}
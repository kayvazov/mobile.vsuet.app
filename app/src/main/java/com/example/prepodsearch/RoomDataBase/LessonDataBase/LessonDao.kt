package com.example.prepodsearch.RoomDataBase.LessonDataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface LessonDao {

    @Query("SELECT * FROM lessonpair where lessonTime like :time AND lessonTeacher like:teacher")
    fun getCurrentLesson(time: String, teacher: String): LiveData<LessonPair>

}
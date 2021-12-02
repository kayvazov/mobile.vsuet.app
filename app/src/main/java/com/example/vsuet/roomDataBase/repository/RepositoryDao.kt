package com.example.vsuet.roomDataBase.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vsuet.API.*

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(data: List<LessonProperty>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRating(data: List<RatingItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(data: List<NewsPost>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroups(data: List<Group>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeachers(data: TeacherProperty)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeacherLessons(data: List<TeacherLesson>)

    @Query("SELECT * FROM lessonProperty")
    fun getSchedule() : List<LessonProperty>

    @Query("SELECT * FROM ratingItem")
    fun getRating() : List<RatingItem>

    @Query("SELECT * FROM newsPost")
    fun getNews() : List<NewsPost>

    @Query("SELECT * FROM `group`")
    fun getGroups() : List<Group>

    @Query("SELECT * FROM teacherproperty")
    fun getTeachers() : TeacherProperty

    @Query("SELECT * FROM teacherlesson")
    fun getTeacherLessons() : List<TeacherLesson>


}
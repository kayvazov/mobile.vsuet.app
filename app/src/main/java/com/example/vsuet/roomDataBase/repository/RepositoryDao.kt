package com.example.vsuet.roomDataBase.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vsuet.API.Group
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.API.NewsPost
import com.example.vsuet.API.RatingItem

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

    @Query("SELECT * FROM lessonProperty")
    fun getSchedule() : List<LessonProperty>

    @Query("SELECT * FROM ratingItem")
    fun getRating() : List<RatingItem>

    @Query("SELECT * FROM newsPost")
    fun getNews() : List<NewsPost>

    @Query("SELECT * FROM `group`")
    fun getGroups() : List<Group>

}
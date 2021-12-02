package com.example.vsuet.roomDataBase.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vsuet.API.*
import com.example.vsuet.TypeConverters.Converters


@Database(entities = [LessonProperty::class, RatingItem::class, NewsPost::class, Group::class, TeacherProperty::class, TeacherLesson::class], version = 6)
@TypeConverters(Converters::class)
abstract class RepositoryDataBase : RoomDatabase() {
    abstract val repositoryDao: RepositoryDao

    companion object {
        @Volatile
        private var INSTANCE: RepositoryDataBase? = null
        fun getInstance(context: Context): RepositoryDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RepositoryDataBase::class.java,
                        "lessonsDatabase"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
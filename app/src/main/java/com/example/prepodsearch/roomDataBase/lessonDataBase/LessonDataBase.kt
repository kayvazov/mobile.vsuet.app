package com.example.prepodsearch.roomDataBase.lessonDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [LessonPair::class], version = 3)
abstract class LessonDataBase : RoomDatabase() {

    abstract val lessonDataBaseDao: LessonDao


    companion object {
        @Volatile
        private var INSTANCE: LessonDataBase? = null

        fun getInstance(context: Context): LessonDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LessonDataBase::class.java,
                        "lessonsDatabase"
                    ).fallbackToDestructiveMigration().createFromAsset("database/lessonPair.db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}
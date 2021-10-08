package com.example.prepodsearch.roomDataBase.teacherDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Teacher::class], version = 5)
abstract class TeacherDataBase : RoomDatabase() {

    abstract val teacherDataBaseDao: TeacherDAO

    companion object {
        @Volatile
        private var INSTANCE: TeacherDataBase? = null

        fun getInstance(context: Context): TeacherDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TeacherDataBase::class.java,
                        "lessonsDatabase"
                    ).fallbackToDestructiveMigration().createFromAsset("database/teacher.db").build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}
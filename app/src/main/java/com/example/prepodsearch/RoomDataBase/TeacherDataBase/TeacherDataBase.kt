package com.example.prepodsearch.RoomDataBase.TeacherDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Teacher::class], version = 0)
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
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}
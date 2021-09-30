package com.example.prepodsearch.MainActivity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prepodsearch.RoomDataBase.LessonDataBase.LessonDao
import com.example.prepodsearch.RoomDataBase.TeacherDataBase.TeacherDAO
import java.lang.IllegalArgumentException

class MainViewModelFactory(val teacherDataSource: TeacherDAO, val lessonDataSource: LessonDao, val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) return MainViewModel(teacherDataSource, lessonDataSource, application) as T
        throw IllegalArgumentException("Wrong Model")
    }

}
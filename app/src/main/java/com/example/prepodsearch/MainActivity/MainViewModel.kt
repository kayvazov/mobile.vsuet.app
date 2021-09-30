package com.example.prepodsearch.MainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.prepodsearch.RoomDataBase.LessonDataBase.LessonDao
import com.example.prepodsearch.RoomDataBase.LessonDataBase.LessonPair
import com.example.prepodsearch.RoomDataBase.TeacherDataBase.Teacher
import com.example.prepodsearch.RoomDataBase.TeacherDataBase.TeacherDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel(val teacherDataSource: TeacherDAO, val lessonDataSource: LessonDao , application: Application) :
    AndroidViewModel(application) {

    fun getFacultyTeachers(faculty: String) : LiveData<List<Teacher>>{
        return teacherDataSource.getFacultyTeachers(faculty)
    }

    fun getCurrentLesson(time: String, teacherName: String) : LiveData<LessonPair>{
        return lessonDataSource.getCurrentLesson(time, teacherName)
    }


}
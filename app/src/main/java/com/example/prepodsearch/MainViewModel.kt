package com.example.prepodsearch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.prepodsearch.RoomDataBase.LessonDataBase.LessonDao
import com.example.prepodsearch.RoomDataBase.LessonDataBase.LessonPair
import com.example.prepodsearch.RoomDataBase.TeacherDataBase.Teacher
import com.example.prepodsearch.RoomDataBase.TeacherDataBase.TeacherDAO

class MainViewModel(val teacherDataSource: TeacherDAO, val lessonDataSource: LessonDao , application: Application) :
    AndroidViewModel(application) {

    fun getFacultyTeachers(faculty: String) : LiveData<List<Teacher>>{
        return teacherDataSource.getFacultyTeachers(faculty)
    }

    fun getCurrentLesson(time: String, teacher: String) : LiveData<LessonPair>{
        return lessonDataSource.getCurrentLesson(time, teacher)
    }


}
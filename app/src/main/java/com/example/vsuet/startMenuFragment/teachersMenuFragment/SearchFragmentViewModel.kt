package com.example.vsuet.startMenuFragment.teachersMenuFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDao
import com.example.vsuet.roomDataBase.lessonDataBase.LessonPair
import com.example.vsuet.roomDataBase.teacherDataBase.Teacher
import com.example.vsuet.roomDataBase.teacherDataBase.TeacherDAO

class SearchFragmentViewModel(
    private val lessonDataSource: LessonDao,
    application: Application
) : AndroidViewModel(application) {

    fun getTeachersLessons(
        teacherName: String,
        day: String,
    ): LiveData<List<LessonPair>> {
        return lessonDataSource.getTeachersLessons(teacherName, day)
    }


}
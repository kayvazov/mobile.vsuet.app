package com.example.prepodsearch.listDialogFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonDao
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonPair
import com.example.prepodsearch.roomDataBase.teacherDataBase.TeacherDAO

class ListDialogViewModel(
    private val lessonDataSource: LessonDao,
    private val teacherDataSource: TeacherDAO,
    application: Application
) : AndroidViewModel(application) {


    fun getTeachersLesson(
        teacherName: String,
        day: String
    ): LiveData<List<LessonPair>> {
        return lessonDataSource.getTeachersLessons(teacherName, day)
    }


}
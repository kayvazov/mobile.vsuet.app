package com.example.vsuet.listDialogFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDao
import com.example.vsuet.roomDataBase.lessonDataBase.LessonPair

class ListDialogViewModel(
    private val lessonDataSource: LessonDao,
    application: Application
) : AndroidViewModel(application) {

    fun getTeachersLesson(
        teacherName: String,
        day: String
    ): LiveData<List<LessonPair>> {
        return lessonDataSource.getTeachersLessons(teacherName, day)
    }

}
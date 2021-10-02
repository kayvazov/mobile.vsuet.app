package com.example.prepodsearch.listDialogFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonDao
import com.example.prepodsearch.roomDataBase.teacherDataBase.TeacherDAO
import java.lang.IllegalArgumentException

class ListViewModelFactory(
    private val lessonDataSource: LessonDao,
    private val teacherDataSource: TeacherDAO,
    val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListDialogViewModel::class.java)) return ListDialogViewModel(
            lessonDataSource,
            teacherDataSource,
            application
        ) as T
        throw IllegalArgumentException("wrong model")
    }

}
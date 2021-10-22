package com.example.prepodsearch.listDialogFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonDao

class ListViewModelFactory(
    private val lessonDataSource: LessonDao,
    val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListDialogViewModel::class.java)) return ListDialogViewModel(
            lessonDataSource,
            application
        ) as T
        throw IllegalArgumentException("wrong model")
    }

}
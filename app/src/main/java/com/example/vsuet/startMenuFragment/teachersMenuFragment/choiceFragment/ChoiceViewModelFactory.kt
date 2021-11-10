package com.example.vsuet.startMenuFragment.teachersMenuFragment.choiceFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.roomDataBase.teacherDataBase.TeacherDAO

class ChoiceViewModelFactory(
    private val teacherDataSource: TeacherDAO,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChoiceViewModel::class.java)) return ChoiceViewModel(
            teacherDataSource,
            application
        ) as T
        throw IllegalArgumentException("Wrong model")
    }

}
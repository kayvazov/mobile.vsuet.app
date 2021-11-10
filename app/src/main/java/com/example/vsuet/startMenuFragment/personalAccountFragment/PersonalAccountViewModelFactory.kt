package com.example.vsuet.startMenuFragment.personalAccountFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDao
import java.lang.IllegalArgumentException

class PersonalAccountViewModelFactory(val application: Application, private val lessonDataSource: LessonDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PersonalAccountViewModel::class.java)){
            return PersonalAccountViewModel(application, lessonDataSource) as T
        }
        throw IllegalArgumentException("noob")
    }

}
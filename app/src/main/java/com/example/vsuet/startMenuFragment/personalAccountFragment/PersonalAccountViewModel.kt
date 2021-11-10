package com.example.vsuet.startMenuFragment.personalAccountFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDao
import com.example.vsuet.roomDataBase.lessonDataBase.LessonPair

class PersonalAccountViewModel(application: Application, private val lessonDataSource: LessonDao) :
    AndroidViewModel(application) {

        fun getGroups() : LiveData<List<LessonPair>>{
            return lessonDataSource.getAllLessons()
        }

}
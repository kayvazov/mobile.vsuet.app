package com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDao
import com.example.vsuet.roomDataBase.lessonDataBase.LessonPair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TabLayoutViewModel(application: Application, private val lessonsDataSource: LessonDao) :
    AndroidViewModel(application) {



    fun setGroupLessons(groupName: String, podGroup: Int) : LiveData<List<LessonPair>>{
        return lessonsDataSource.getGroupLessons(groupName, podGroup)
    }

}
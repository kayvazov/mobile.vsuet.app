package com.example.prepodsearch.searchFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonDao
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonPair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
package com.example.vsuet.startMenuFragment.scheduleMenuFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDao
import com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment.TabLayoutViewModel
import java.lang.IllegalArgumentException

class ScheduleViewModelFactory(
    val application: Application,
    private val lessonsDataSource: LessonDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            return ScheduleViewModel(application, lessonsDataSource) as T
        } else {
            throw IllegalArgumentException("OmegaLUL")
        }
    }

}
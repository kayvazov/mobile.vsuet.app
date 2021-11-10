package com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDao
import java.lang.IllegalArgumentException

class TabLayoutViewModelFactory(
    val application: Application,
    val lessonsDataSource: LessonDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TabLayoutViewModel::class.java)) {
            return TabLayoutViewModel(application, lessonsDataSource) as T
        } else {
            throw IllegalArgumentException("OmegaLUL")
        }
    }

}
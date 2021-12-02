package com.example.vsuet.startMenuFragment.scheduleMenuFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.roomDataBase.repository.RepositoryDao

class ScheduleViewModelFactory(
    val application: Application,
    private val repositoryDataSource: RepositoryDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            return ScheduleViewModel(application, repositoryDataSource) as T
        } else {
            throw IllegalArgumentException("OmegaLUL")
        }
    }

}
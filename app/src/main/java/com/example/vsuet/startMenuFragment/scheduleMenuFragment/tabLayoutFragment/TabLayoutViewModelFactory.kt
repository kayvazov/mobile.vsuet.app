package com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TabLayoutViewModelFactory(
    val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TabLayoutViewModel::class.java)) {
            return TabLayoutViewModel(application) as T
        } else {
            throw IllegalArgumentException("OmegaLUL")
        }
    }

}
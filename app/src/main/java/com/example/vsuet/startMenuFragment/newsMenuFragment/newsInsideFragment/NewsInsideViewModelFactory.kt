package com.example.vsuet.startMenuFragment.newsMenuFragment.newsInsideFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class NewsInsideViewModelFactory(private val insideLink: String, val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsInsideViewModel::class.java)){
            return NewsInsideViewModel(insideLink, application) as T
        }
        throw IllegalArgumentException("oh shit i'm sorry")
    }

}
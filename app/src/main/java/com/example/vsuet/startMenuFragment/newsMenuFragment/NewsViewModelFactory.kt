package com.example.vsuet.startMenuFragment.newsMenuFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.roomDataBase.repository.RepositoryDao


class NewsViewModelFactory(
    val application: Application,
    private val repositoryDataSource: RepositoryDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(application, repositoryDataSource) as T
        } else {
            throw IllegalArgumentException("OmegaLUL")
        }
    }

}
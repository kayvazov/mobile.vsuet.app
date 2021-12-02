package com.example.vsuet.startMenuFragment.teachersMenuFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.roomDataBase.repository.RepositoryDao

class SearchFragmentFactory(
    private val repositoryDao: RepositoryDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchFragmentViewModel::class.java)) return SearchFragmentViewModel(
            application, repositoryDao
        ) as T
        throw IllegalArgumentException("wrong model")
    }

}
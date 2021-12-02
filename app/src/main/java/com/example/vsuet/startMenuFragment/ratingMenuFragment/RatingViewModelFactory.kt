package com.example.vsuet.startMenuFragment.ratingMenuFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.roomDataBase.repository.RepositoryDao

class RatingViewModelFactory(
    val application: Application,
    private val repositoryDataSource: RepositoryDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RatingViewModel::class.java)) {
            return RatingViewModel(application, repositoryDataSource) as T
        } else {
            throw IllegalArgumentException("OmegaLUL")
        }
    }

}
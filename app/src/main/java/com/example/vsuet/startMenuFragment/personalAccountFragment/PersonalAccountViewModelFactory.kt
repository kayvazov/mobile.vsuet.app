package com.example.vsuet.startMenuFragment.personalAccountFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.roomDataBase.repository.RepositoryDao
import java.lang.IllegalArgumentException

class PersonalAccountViewModelFactory(val application: Application, private val repositoryDataSource: RepositoryDao) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PersonalAccountViewModel::class.java)){
            return PersonalAccountViewModel(application, repositoryDataSource) as T
        }
        throw IllegalArgumentException("noob")
    }

}
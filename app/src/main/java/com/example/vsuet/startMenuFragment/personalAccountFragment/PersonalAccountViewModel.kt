package com.example.vsuet.startMenuFragment.personalAccountFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vsuet.API.*
import com.example.vsuet.roomDataBase.repository.RepositoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonalAccountViewModel(application: Application, val repositoryDataSource: RepositoryDao) :
    AndroidViewModel(application) {

    val groups = MutableLiveData<List<Group>>()
    val internetConnection = MutableLiveData<Boolean>()
    val faculty = MutableLiveData<String>()
    val group = MutableLiveData<String>()

    fun getGroups() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                GroupsApi.retrofitService.getProperties().enqueue(
                    object : Callback<GroupProperty> {
                        override fun onResponse(
                            call: Call<GroupProperty>,
                            response: Response<GroupProperty>
                        ) {
                            internetConnection.value = true
                            groups.value = response.body()?.data
                            if (response.body()?.data != null && response.body()?.data!!.isNotEmpty()) {
                                insertData(response.body()?.data!!)
                            }
                        }

                        override fun onFailure(call: Call<GroupProperty>, t: Throwable) {
                            println(t.message)
                            internetConnection.value = false
                        }

                    }
                )
            }
        }
    }

    fun validateStudent(recordBookNum: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                RecordApi.retrofitService.getProperties(recordBookNum).enqueue(
                    object : Callback<RecordProperty> {
                        override fun onResponse(
                            call: Call<RecordProperty>,
                            response: Response<RecordProperty>
                        ) {
                            if (response.body()?.data == null) {
                                group.value = "Invalid"
                            } else {
                                group.value = response.body()?.data!!.groups[0].name
                            }
                        }

                        override fun onFailure(call: Call<RecordProperty>, t: Throwable) {
                            println(t.message)
                        }
                    }
                )
            }
        }
    }

    fun getData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                groups.postValue(repositoryDataSource.getGroups())
            }
        }
    }

    fun insertData(data: List<Group>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repositoryDataSource.insertGroups(data)
            }
        }
    }


}
package com.example.vsuet.startMenuFragment.scheduleMenuFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.API.ScheduleProperty
import com.example.vsuet.API.WallApi
import com.example.vsuet.roomDataBase.repository.RepositoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleViewModel(application: Application, private val repositoryDataSource: RepositoryDao) :
    AndroidViewModel(application) {


    val schedule = MutableLiveData<List<LessonProperty>>()
    val updatedTime = MutableLiveData<String>()

    fun getSchedule(groupName: String, time: String, isNewGroup: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                WallApi.retrofitService.getProperties(groupName, time).enqueue(
                    object : Callback<ScheduleProperty> {
                        override fun onResponse(
                            call: Call<ScheduleProperty>,
                            response: Response<ScheduleProperty>
                        ) {
                            schedule.value = response.body()?.data?.lessons
                            updatedTime.value = response.body()?.data?.updatedTime
                            if(response.body()?.data?.lessons!!.isNotEmpty() && (updatedTime.value != time || isNewGroup)) {
                                insertData(response.body()?.data?.lessons!!)
                            }

                            if(updatedTime.value == time && !isNewGroup){
                                getData()
                            }

                        }

                        override fun onFailure(call: Call<ScheduleProperty>, t: Throwable) {
                            if(isNewGroup){
                                println(t.message)
                            } else {
                                println(t.message)
                                getData()
                            }
                        }

                    }
                )
            }
        }
    }

    fun getData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                schedule.postValue(repositoryDataSource.getSchedule())
                println("It is working fine")
            }
        }
    }

    fun insertData(data: List<LessonProperty>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repositoryDataSource.insertSchedule(data)
            }
        }
    }

}
package com.example.vsuet.startMenuFragment.teachersMenuFragment.choiceFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vsuet.API.*
import com.example.vsuet.roomDataBase.repository.RepositoryDao
import com.example.vsuet.roomDataBase.teacherDataBase.Teacher
import com.example.vsuet.roomDataBase.teacherDataBase.TeacherDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChoiceViewModel(private val repositoryDataSource: RepositoryDao, application: Application) :
    AndroidViewModel(application) {

    val teachers = MutableLiveData<TeacherProperty>()

    fun getTeachers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                TeacherApi.retrofitService.getProperties().enqueue(
                    object : Callback<TeacherProperty> {
                        override fun onResponse(
                            call: Call<TeacherProperty>,
                            response: Response<TeacherProperty>
                        ) {
                            teachers.value = response.body()
                            if(response.body()?.data != null) {
                                insertData(response.body()!!)
                            }
                        }
                        override fun onFailure(call: Call<TeacherProperty>, t: Throwable) {
                            println(t.message)
                            getData()
                        }
                    }
                )
            }
        }
    }

    fun getData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                teachers.postValue(repositoryDataSource.getTeachers())
            }
        }
    }

    fun insertData(data: TeacherProperty){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repositoryDataSource.insertTeachers(data)
            }
        }
    }

}
package com.example.vsuet.startMenuFragment.teachersMenuFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vsuet.API.TeacherLesson
import com.example.vsuet.API.TeacherLessonApi
import com.example.vsuet.API.TeacherLessonProperty
import com.example.vsuet.API.TeacherProperty
import com.example.vsuet.roomDataBase.repository.RepositoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragmentViewModel(
    application: Application,
    val repositoryDataSource: RepositoryDao
) : AndroidViewModel(application) {

    val teacherLessons = MutableLiveData<List<TeacherLesson>>()

    fun getTeachersLessons(name: String, isDataBaseCreated: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                TeacherLessonApi.retrofitService.getProperties(name).enqueue(
                    object : Callback<TeacherLessonProperty> {
                        override fun onResponse(
                            call: Call<TeacherLessonProperty>,
                            response: Response<TeacherLessonProperty>
                        ) {
                            println(response.body()?.data)
                            teacherLessons.value = response.body()?.data

                            if(response.body()?.data != null){
                                insertData(response.body()?.data!!)
                            }

                        }
                        override fun onFailure(call: Call<TeacherLessonProperty>, t: Throwable) {
                            if(isDataBaseCreated) {
                                getData()
                            }
                            println(t.message)
                        }
                    }
                )
            }
        }
    }

    fun getData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                teacherLessons.postValue(repositoryDataSource.getTeacherLessons())
            }
        }
    }

    fun insertData(data: List<TeacherLesson>){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repositoryDataSource.insertTeacherLessons(data)
            }
        }
    }



}
package com.example.vsuet.startMenuFragment.teachersMenuFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vsuet.API.*
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDao
import com.example.vsuet.roomDataBase.lessonDataBase.LessonPair
import com.example.vsuet.roomDataBase.repository.RepositoryDao
import com.example.vsuet.roomDataBase.teacherDataBase.Teacher
import com.example.vsuet.roomDataBase.teacherDataBase.TeacherDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragmentViewModel(
    private val repositoryDataSource: RepositoryDao,
    application: Application
) : AndroidViewModel(application) {

    val teacherLessons = MutableLiveData<List<TeacherLesson>>()

    fun getTeachers(name: String) {
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

                        }
                        override fun onFailure(call: Call<TeacherLessonProperty>, t: Throwable) {
                            println(t.message)
                            println("COCK")
                        }
                    }
                )
            }
        }
    }




}
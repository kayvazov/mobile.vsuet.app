package com.example.vsuet.startMenuFragment.teachersMenuFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vsuet.API.TeacherLesson
import com.example.vsuet.API.TeacherLessonApi
import com.example.vsuet.API.TeacherLessonProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragmentViewModel(
    application: Application
) : AndroidViewModel(application) {

    val teacherLessons = MutableLiveData<List<TeacherLesson>>()

    fun getTeachersLessons(name: String) {
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
                        }
                    }
                )
            }
        }
    }




}
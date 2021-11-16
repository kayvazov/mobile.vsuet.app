package com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDao
import com.example.vsuet.roomDataBase.lessonDataBase.LessonPair
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.API.ScheduleProperty
import com.example.vsuet.API.WallApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class TabLayoutViewModel(application: Application, private val lessonsDataSource: LessonDao) :
    AndroidViewModel(application) {

    val _schedule = MutableLiveData<List<LessonProperty>>()

    fun setGroupLessons(groupName: String, podGroup: Int): LiveData<List<LessonPair>> {
        return lessonsDataSource.getGroupLessons(groupName, podGroup)
    }

    fun getSchedule(groupName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                WallApi.retrofitService.getProperties(groupName, "").enqueue(
                    object : retrofit2.Callback<ScheduleProperty> {
                        override fun onResponse(
                            call: Call<ScheduleProperty>,
                            response: Response<ScheduleProperty>
                        ) {
                            _schedule.value = response.body()?.data?.lessons
                        }

                        override fun onFailure(call: Call<ScheduleProperty>, t: Throwable) {
                            println("?!")
                            println(t.message)
                        }

                    }
                )
            }
        }
    }


}
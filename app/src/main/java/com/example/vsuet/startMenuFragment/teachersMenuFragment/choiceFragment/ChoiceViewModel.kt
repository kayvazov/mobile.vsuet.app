package com.example.vsuet.startMenuFragment.teachersMenuFragment.choiceFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vsuet.roomDataBase.teacherDataBase.Teacher
import com.example.vsuet.roomDataBase.teacherDataBase.TeacherDAO

class ChoiceViewModel(private val teacherDataSource: TeacherDAO, application: Application) :
    AndroidViewModel(application) {

    fun getTeachers(): LiveData<List<Teacher>> {
        return teacherDataSource.getAllTeachers()
    }

}
package com.example.prepodsearch.listDialogFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.prepodsearch.roomDataBase.teacherDataBase.Teacher
import com.example.prepodsearch.roomDataBase.teacherDataBase.TeacherDAO

class ListDialogViewModel(private val teacherDataSource: TeacherDAO, application: Application) : AndroidViewModel(application) {

    fun getFacultyTeachers(faculty: String) : LiveData<List<Teacher>> {
        return teacherDataSource.getFacultyTeachers(faculty)
    }


}
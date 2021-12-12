package com.example.vsuet.teacherRecyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.R

class TeacherViewHolder(lessonTicket: View) : RecyclerView.ViewHolder(lessonTicket) {
    val lessonTime : TextView = lessonTicket.findViewById(R.id.lessonTime)
    val lessonName : TextView = lessonTicket.findViewById(R.id.lessonName)
    val lessonTeacher : TextView = lessonTicket.findViewById(R.id.lessonTeacher)
    val lessonClass : TextView = lessonTicket.findViewById(R.id.lessonClass)
    val lessonIndicator : TextView = lessonTicket.findViewById(R.id.lessonIndicator)
    val lessonType: TextView = lessonTicket.findViewById(R.id.lessonType)
}
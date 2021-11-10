package com.example.vsuet.teacherRecyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.R

class TeacherViewHolder(lessonTicket: View) : RecyclerView.ViewHolder(lessonTicket) {
    var lessonName: TextView = lessonTicket.findViewById(R.id.lessonTicketName)
    var lessonClass: TextView = lessonTicket.findViewById(R.id.lessonTicketClass)
    var lessonTime: TextView = lessonTicket.findViewById(R.id.lessonTicketTime)
}
package com.example.prepodsearch.recyclerViewAdapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prepodsearch.R

class LessonViewHolder(lessonTicket: View) : RecyclerView.ViewHolder(lessonTicket) {

    var lessonName: TextView = lessonTicket.findViewById(R.id.lessonTicketName)
    var lessonClass: TextView = lessonTicket.findViewById(R.id.lessonTicketClass)
    var lessonTime: TextView = lessonTicket.findViewById(R.id.lessonTicketTime)

}
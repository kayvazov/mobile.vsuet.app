package com.example.prepodsearch.RecyclerViewAdapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prepodsearch.R

class LessonViewHolder(lessonTicket: View) : RecyclerView.ViewHolder(lessonTicket) {

    var lessonName = lessonTicket.findViewById<TextView>(R.id.lessonTicketName)
    var lessonClass = lessonTicket.findViewById<TextView>(R.id.lessonTicketClass)
    var lessonTime = lessonTicket.findViewById<TextView>(R.id.lessonTicketTime)

}
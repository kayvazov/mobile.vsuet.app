package com.example.vsuet.teacherRecyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.R
import com.example.vsuet.roomDataBase.lessonDataBase.LessonPair

class TeacherRecyclerViewAdapter : RecyclerView.Adapter<TeacherViewHolder>() {

    var data = listOf<LessonPair>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val item = data[position]
        holder.apply {
            lessonClass.text = item.lessonClass + " ауд."
            lessonName.text = item.lessonName
            lessonTime.text = item.lessonTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.lesson_view_holder_item, parent, false)
        return TeacherViewHolder(view)
    }


}
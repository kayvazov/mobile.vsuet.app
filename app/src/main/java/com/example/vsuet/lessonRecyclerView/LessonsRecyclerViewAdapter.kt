package com.example.vsuet.lessonRecyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.R
import com.example.vsuet.roomDataBase.lessonDataBase.LessonPair


class LessonsRecyclerViewAdapter(
) : RecyclerView.Adapter<LessonViewHolder>() {

    var data = listOf<LessonPair>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.lesson_ticket, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val item = data[position]

        holder.apply {
            lessonClass.text = item.lessonClass + " ауд."
            lessonName.text = item.lessonName
            lessonTeacher.text = item.lessonTeacher
            lessonTime.text = item.lessonTime
        }
    }
}
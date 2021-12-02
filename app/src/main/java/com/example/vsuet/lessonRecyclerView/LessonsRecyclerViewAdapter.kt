package com.example.vsuet.lessonRecyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.R


class LessonsRecyclerViewAdapter : RecyclerView.Adapter<LessonViewHolder>() {

    var data = listOf<LessonProperty>()
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val item = data[position]

        holder.apply {
            lessonClass.text = item.audience
            lessonName.text = item.name
            lessonTeacher.text = item.teacher
            lessonTime.text = item.time.start + " - " + item.time.end
        }
    }
}
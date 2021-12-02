package com.example.vsuet.teacherRecyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.API.TeacherLesson
import com.example.vsuet.R

class TeacherRecyclerViewAdapter : RecyclerView.Adapter<TeacherViewHolder>() {

    var data = listOf<TeacherLesson>()
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
            lessonClass.text = item.audience
            lessonName.text = item.name
            lessonTime.text = item.time.start + "-" + item.time.end
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.lesson_view_holder_item, parent, false)
        return TeacherViewHolder(view)
    }


}
package com.example.prepodsearch.recyclerViewAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prepodsearch.R
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonPair

class LessonRecyclerViewAdapter : RecyclerView.Adapter<LessonViewHolder>() {

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
    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val item = data[position]
        holder.apply {
            lessonClass.text = item.lessonClass + " ауд."
            lessonName.text = item.lessonName
            lessonTime.text = item.lessonTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.lesson_view_holder_item, parent, false)
        return LessonViewHolder(view)
    }


}
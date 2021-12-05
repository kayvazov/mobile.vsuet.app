package com.example.vsuet.lessonRecyclerView

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.R
import java.util.*
import kotlin.math.min


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
        println(item)

        val calendar = Calendar.getInstance()
        val hours = calendar.get(Calendar.HOUR)
        val minutes = calendar.get(Calendar.MINUTE)


        holder.apply {
            lessonClass.text = lessonClass.text.toString() + item.audience
            lessonName.text = lessonName.text.toString() + item.name
            lessonTeacher.text = lessonTeacher.text.toString() + item.teacher
            lessonTime.text = lessonTime.text.toString() + item.time.start + " - " + item.time.end
            if (item.type != "") {
                lessonType.text = item.type
            } else {
                lessonType.visibility = View.GONE
                val typeMarginToChange = lessonType.layoutParams as ConstraintLayout.LayoutParams
                typeMarginToChange.setMargins(0, 0, 0, 0)
            }


            val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                Calendar.TUESDAY -> "вторник"
                Calendar.WEDNESDAY -> "среда"
                Calendar.THURSDAY -> "четверг"
                Calendar.FRIDAY -> "пятница"
                Calendar.SATURDAY -> "суббота"
                Calendar.SUNDAY -> "воскресенье"
                else -> "понедельник"
            }

            val startTimeHours = item.time.start.split(".")[0].toInt()
            val endTimeHours = item.time.end.split(".")[0].toInt()
            val startTimeMinutes = item.time.start.split(".")[1].toInt()
            val endTimeMinutes = item.time.end.split(".")[1].toInt()

            val nameMarginToChange = lessonName.layoutParams as ConstraintLayout.LayoutParams
            if (((startTimeHours % 12 < hours && endTimeHours % 12 >= hours && endTimeMinutes < minutes) || ((startTimeHours % 12 == hours && startTimeMinutes > minutes) || (endTimeHours % 12 == hours && endTimeMinutes < minutes))) && item.day == dayOfWeek) {
                println(item.time)
                println(hours)
                println(minutes)
                lessonIndicator.text = "Занятие идёт"
                lessonIndicator.setTextColor(Color.parseColor("#FF0000"))
                item.time.isCurrent = true
                println("CURRENT")
            } else if (data.indexOf(item) != 0) {
                if (data[position - 1].time.isCurrent) {
                    item.time.isCurrent = false
                    lessonIndicator.text = "Следующая пара"
                } else {
                    item.time.isCurrent = false
                    lessonIndicator.visibility = View.GONE
                    nameMarginToChange.setMargins(0, 0, 0, 0)
                }
            } else {
                item.time.isCurrent = false
                lessonIndicator.visibility = View.GONE
                nameMarginToChange.setMargins(0, 0, 0, 0)
            }

        }


    }
}
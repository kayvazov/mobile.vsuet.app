package com.example.vsuet.teacherRecyclerView

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.vsuet.API.TeacherLesson
import com.example.vsuet.R
import java.util.*

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
                lessonIndicator.text = "Занятие идёт"
                lessonIndicator.setTextColor(Color.parseColor("#FF0000"))
                item.time.isCurrent = true
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.lesson_view_holder_item, parent, false)
        return TeacherViewHolder(view)
    }


}
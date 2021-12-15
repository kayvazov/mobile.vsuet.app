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
import java.text.SimpleDateFormat
import java.util.*


class LessonsRecyclerViewAdapter : RecyclerView.Adapter<LessonViewHolder>() {

    private fun timeComparator(firstTime: List<String>, secondTime: List<String>): Boolean {

        return if (firstTime[0] != "" && secondTime[0] != "" && firstTime[0] != secondTime[0]) {
            firstTime[0].toInt() > secondTime[0].toInt()
        } else {
            if (firstTime[1] != "" && secondTime[1] != "") {
                firstTime[1].toInt() >= secondTime[1].toInt()
            } else {
                if (firstTime[1] == "" && secondTime[1] == "") {
                    true
                } else firstTime[1] != ""
            }
        }


    }

    private var nextPairTime = "0"
    private val breakList = mutableListOf<LessonProperty>()


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


        val date = Date()
        val formatterHours = SimpleDateFormat("HH")
        val formatterMinutes = SimpleDateFormat("mm")
        val hours = formatterHours.format(date)
        val minutes = formatterMinutes.format(date)
        val hoursMinutes = listOf(hours, minutes)

        val pairTimeVaries = listOf(
            "8.00-9.45",
            "9.45-11.20",
            "11.50-13.25",
            "13.35-15.10",
            "15.20-16.55",
            "17.05-18.40",
            "18.50-20.25"
        )

        val pairTime = if (timeComparator(hoursMinutes, "8:00".split(":")) && timeComparator(
                "9:35".split(":"),
                hoursMinutes
            )
        ) {
            pairTimeVaries[0]
        } else if (timeComparator(
                hoursMinutes,
                "9:45".split(":")
            ) && timeComparator("11:20".split(":"), hoursMinutes)
        ) {
            pairTimeVaries[1]
        } else if (timeComparator(hoursMinutes, "11:50".split(":")) && timeComparator(
                "13:25".split(
                    ":"
                ), hoursMinutes
            )
        ) {
            pairTimeVaries[2]
        } else if (timeComparator(hoursMinutes, "13:35".split(":")) && timeComparator(
                "15:10".split(
                    ":"
                ), hoursMinutes
            )
        ) {
            pairTimeVaries[3]
        } else if (timeComparator(hoursMinutes, "15:20".split(":")) && timeComparator(
                "16:55".split(
                    ":"
                ), hoursMinutes
            )
        ) {
            pairTimeVaries[4]
        } else if (timeComparator(hoursMinutes, "17:05".split(":")) && timeComparator(
                "18:40".split(
                    ":"
                ), hoursMinutes
            )
        ) {
            pairTimeVaries[5]
        } else if (timeComparator(hoursMinutes, "18:50".split(":")) && timeComparator(
                "20:25".split(
                    ":"
                ), hoursMinutes
            )
        ) {
            pairTimeVaries[6]
        } else "Перерыв"


        val currentLessonTime = item.time.start + "-" + item.time.end
        if (pairTime == "Перерыв") {
            for (i in data) {
                if (timeComparator(i.time.start.split("."), hoursMinutes)) {
                    breakList.add(i)
                }
            }
        }



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

            var numerator =
                Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) % 2 == 0


            val nameMarginToChange = lessonName.layoutParams as ConstraintLayout.LayoutParams
            if (currentLessonTime == pairTime && item.day == dayOfWeek && item.weekType == numerator) {
                lessonIndicator.text = "Занятие идёт"
                println(currentLessonTime)
                println(pairTime)
                println(hoursMinutes)
                lessonIndicator.setTextColor(Color.parseColor("#FF0000"))
            } else if (nextPairTime == currentLessonTime) {
                lessonIndicator.text = "Следующая пара"
            } else {
                lessonIndicator.visibility = View.GONE
                nameMarginToChange.setMargins(0, 0, 0, 0)
            }

        }

        if (data.indexOf(item) + 1 < data.size) {
            nextPairTime = if (pairTime == "Перерыв") {
                breakList.first().time.start + "-" + breakList.first().time.end
            } else if(pairTime == currentLessonTime) {
                data[data.indexOf(item) + 1].time.start + "-" + data[data.indexOf(
                    item
                ) + 1].time.end
            } else {
                "0"
            }
        }



    }
}
package com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.databinding.TabLayoutFragmentBinding
import com.example.vsuet.lessonRecyclerView.LessonsRecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.*

class TabLayoutFragment(
    private val day: String?,
    private val numerator: Boolean,
    private val data: List<LessonProperty>
) : Fragment() {

    private lateinit var viewModel: TabLayoutViewModel
    private lateinit var viewModelFactory: TabLayoutViewModelFactory
    private lateinit var binding: TabLayoutFragmentBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TabLayoutFragmentBinding.inflate(inflater)


        val application = requireNotNull(this.activity).application

        binding.lessonsList.apply {
            alpha = 0f
            animate().alpha(1.0f).duration = 500
            layoutManager = LinearLayoutManager(requireActivity())
        }

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

        viewModelFactory = TabLayoutViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[TabLayoutViewModel::class.java]
        val recyclerAdapter = LessonsRecyclerViewAdapter()
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

        val personalAccountSettings =
            requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
        val groupName = personalAccountSettings.getString("groupNumber", "Не указана группа")!!
        val podGroup = personalAccountSettings.getString("underGroupNumber", "1")!!
        if (data.isNotEmpty()) {

            val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                Calendar.TUESDAY -> "вторник"
                Calendar.WEDNESDAY -> "среда"
                Calendar.THURSDAY -> "четверг"
                Calendar.FRIDAY -> "пятница"
                Calendar.SATURDAY -> "суббота"
                Calendar.SUNDAY -> "воскресенье"
                else -> "понедельник"
            }

            val time = data.last().time
            val startTimeHours = time.start.split(".")[0].toInt()
            val endTimeHours = time.end.split(".")[0].toInt()
            val startTimeMinutes = time.start.split(".")[1].toInt()
            val endTimeMinutes = time.end.split(".")[1].toInt()


            val date = Date()
            val formatterHours = SimpleDateFormat("HH")
            val formatterMinutes = SimpleDateFormat("mm")
            val hours = formatterHours.format(date).toInt()
            val minutes = formatterMinutes.format(date).toInt()

            println(day)
            println(dayOfWeek)

            val currentNumerator =
                Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) % 2 == 0

            if (groupName != "Не указана группа") {
                recyclerAdapter.data =
                    data.filter { it.day == day && it.weekType == numerator && it.subgroup == podGroup.toInt() && groupName == it.group }
                if ((recyclerAdapter.data.isEmpty() || (endTimeHours < hours || (endTimeMinutes < minutes && endTimeHours == hours))) && day == dayOfWeek) {
                    println("?")
                    binding.noLessonsText.visibility = View.VISIBLE
                    val noLessonsMargin =
                        binding.noLessonsText.layoutParams as ConstraintLayout.LayoutParams
                    noLessonsMargin.setMargins(0, 30, 0, 10)
                    var nextDay = ""
                    val days = listOf(
                        "понедельник",
                        "вторник",
                        "среда",
                        "четверг",
                        "пятница",
                        "суббота",
                        "воскресенье"
                    )
                    for (i in days.indexOf(day) + 1 until days.size) {
                        if (data.any { it.day == days[i] && it.weekType == currentNumerator && it.subgroup == podGroup.toInt() && groupName == it.group }) {
                            nextDay = days[i]
                            break
                        }
                    }
                    val dayList = listOf(
                        "понедельник",
                        "вторник",
                        "среду",
                        "четверг",
                        "пятницу",
                        "субботу",
                        "воскресенье"
                    )
                    if (nextDay == "") {
                        for (i in days) {
                            if (data.any { it.day == i && it.weekType == !currentNumerator && it.subgroup == podGroup.toInt() && groupName == it.group }) {
                                nextDay = i
                                if (nextDay == "суббота" || nextDay == "восресенье") {
                                    recyclerAdapter.data =
                                        data.filter { it.day == nextDay && it.subgroup == podGroup.toInt() && it.weekType == !currentNumerator && groupName == it.group }
                                } else {
                                    recyclerAdapter.data =
                                        data.filter { it.day == nextDay && it.subgroup == podGroup.toInt() && it.weekType == !currentNumerator && groupName == it.group }
                                }
                                val fineNextDayText = dayList[days.indexOf(nextDay)]
                                binding.noLessonsText.text =
                                    "На сегодня всё, пары на $fineNextDayText:"
                                break
                            }
                        }
                    } else {
                        val fineNextDayText = dayList[days.indexOf(nextDay)]
                        binding.noLessonsText.text = "На сегодня всё, пары на $fineNextDayText:"
                        recyclerAdapter.data =
                            data.filter { it.day == nextDay && it.subgroup == podGroup.toInt() && it.weekType == numerator && groupName == it.group }
                    }
                } else if (recyclerAdapter.data.isEmpty()) {
                    binding.noLessonsText.visibility = View.VISIBLE
                    binding.noLessonsText.text = "Сегодня нет пар"
                }
            } else {
                binding.noGroupText.apply {
                    visibility = View.VISIBLE
                    alpha = 0f
                    animate().alpha(1.0f).duration = 500
                }
            }

            binding.lessonsList.adapter = recyclerAdapter
        }



        return binding.root
    }

}
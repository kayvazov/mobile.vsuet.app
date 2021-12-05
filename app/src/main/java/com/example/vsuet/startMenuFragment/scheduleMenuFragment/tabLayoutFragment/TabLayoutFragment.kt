package com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.databinding.TabLayoutFragmentBinding
import com.example.vsuet.lessonRecyclerView.LessonsRecyclerViewAdapter
import java.util.*

class TabLayoutFragment(
    private val day: String?,
    private val numerator: Boolean,
    private val data: List<LessonProperty>
) : Fragment() {

    private lateinit var viewModel: TabLayoutViewModel
    private lateinit var viewModelFactory: TabLayoutViewModelFactory
    private lateinit var binding: TabLayoutFragmentBinding

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

        viewModelFactory = TabLayoutViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TabLayoutViewModel::class.java)
        val recyclerAdapter = LessonsRecyclerViewAdapter()
        val personalAccountSettings =
            requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
        val groupName = personalAccountSettings.getString("groupNumber", "Не указана группа")!!
        val podGroup = personalAccountSettings.getString("underGroupNumber", "1")!!
        if(data.isNotEmpty()) {

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

            val calendar = Calendar.getInstance()
            val hours = calendar.get(Calendar.HOUR)
            val minutes = calendar.get(Calendar.MINUTE)

            if (groupName != "Не указана группа") {
                recyclerAdapter.data =
                    data.filter { it.day == day && it.weekType == numerator && it.subgroup == podGroup.toInt() }
                if (recyclerAdapter.data.isEmpty() || (endTimeHours % 12 < hours || (endTimeMinutes < minutes && endTimeHours % 12 == hours)) && day == dayOfWeek) {
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
                        if (data.any { it.day == days[i] && it.weekType == numerator && it.subgroup == podGroup.toInt() }) {
                            nextDay = days[i]
                            break
                        }
                    }
                    if (nextDay == "") {
                        for (i in days) {
                            if (data.any { it.day == i && it.weekType == !numerator && it.subgroup == podGroup.toInt() }) {
                                println("?")
                                nextDay = i
                                println(nextDay)
                                if (nextDay == "суббота" || nextDay == "восресенье") {
                                    recyclerAdapter.data =
                                        data.filter { it.day == nextDay && it.subgroup == podGroup.toInt() && it.weekType == !numerator}
                                } else {
                                    println("????")
                                    recyclerAdapter.data =
                                        data.filter { it.day == nextDay && it.subgroup == podGroup.toInt() && it.weekType == !numerator}
                                    println()
                                }
                                break
                            }
                        }
                    } else {
                        recyclerAdapter.data =
                            data.filter { it.day == nextDay && it.subgroup == podGroup.toInt() && it.weekType == numerator}
                    }
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
package com.example.vsuet.startMenuFragment.scheduleMenuFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.databinding.ScheduleFragmentBinding
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDataBase
import com.example.vsuet.viewPagerAdapter.DaysViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class ScheduleFragment : Fragment() {


    private lateinit var binding: ScheduleFragmentBinding
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var viewModelFactory: ScheduleViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScheduleFragmentBinding.inflate(inflater)
        var numerator =
            if (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1 % 2 == 1) {
                binding.scheduleNumeratorButton.text = "Числитель"
                true
            } else {
                binding.scheduleNumeratorButton.text = "Знаменатель"
                false
            }

        val days = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ")

        val application = requireNotNull(this.activity).application
        val lessonsDataBase = LessonDataBase.getInstance(requireContext())
        val lessonsDataSource = lessonsDataBase.lessonDataBaseDao

        viewModelFactory = ScheduleViewModelFactory(application, lessonsDataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel::class.java)


        val personalAccountSettings =
            requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
        val groupName = personalAccountSettings.getString("groupNumber", "Не указана группа")!!
        val updatedTime = personalAccountSettings.getString("updatedTime", "")!!
        viewModel.getSchedule(groupName, updatedTime)

        viewModel.updatedTime.observeForever {
            personalAccountSettings.edit().putString("updatedTime", it).apply()
        }
        viewModel._schedule.observeForever { list ->
            if (list.isEmpty()) {
                viewModel.getSchedule(groupName, "")
            }
            binding.apply {

                scheduleNumeratorButton.setOnClickListener {
                    val currentItem = daysPager.currentItem
                    numerator = if (!numerator) {
                        scheduleNumeratorButton.text = "Числитель"
                        true
                    } else {
                        scheduleNumeratorButton.text = "Знаменатель"
                        false
                    }


                    daysPager.adapter =
                        DaysViewPagerAdapter(
                            requireActivity().supportFragmentManager,
                            lifecycle,
                            numerator,
                            list
                        )
                    daysPager.setCurrentItem(currentItem, false)
                    TabLayoutMediator(daysTabLayout, daysPager) { tab, position ->
                        tab.text = days[position]
                    }.attach()

                }

                daysPager.adapter =
                    DaysViewPagerAdapter(
                        requireActivity().supportFragmentManager,
                        lifecycle,
                        numerator,
                        list
                    )
                TabLayoutMediator(daysTabLayout, daysPager) { tab, position ->
                    tab.text = days[position]
                }.attach()
            }
        }
        return binding.root
    }
}
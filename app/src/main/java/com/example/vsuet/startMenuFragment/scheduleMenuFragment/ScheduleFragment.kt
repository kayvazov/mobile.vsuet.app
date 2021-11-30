package com.example.vsuet.startMenuFragment.scheduleMenuFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.databinding.ScheduleFragmentBinding
import com.example.vsuet.roomDataBase.repository.RepositoryDataBase
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

        val days = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС")

        val application = requireNotNull(this.activity).application
        val repository = RepositoryDataBase.getInstance(application)
        val repositoryDataSource = repository.repositoryDao

        viewModelFactory = ScheduleViewModelFactory(application, repositoryDataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel::class.java)


        val personalAccountSettings =
            requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
        val groupName = personalAccountSettings.getString("groupNumber", "Не указана группа")!!
        val updatedTime = personalAccountSettings.getString("updatedTime", "")!!
        val isNewGroup = personalAccountSettings.getBoolean("isGroupChanged", false)
        println(isNewGroup)
        if (isNewGroup) {
            viewModel.getSchedule(groupName, "", isNewGroup)
            personalAccountSettings.edit().putBoolean("isGroupChanged", false).apply()
        } else {
            viewModel.getSchedule(groupName, updatedTime, isNewGroup)
        }


        viewModel.schedule.observe(viewLifecycleOwner) { list ->
            viewModel.updatedTime.observe(viewLifecycleOwner) {
                personalAccountSettings.edit().putString("updatedTime", it).apply()
            }

            binding.apply {
                progressBar2.visibility = View.GONE
                daysTabLayout.visibility = View.VISIBLE

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

                val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                binding.daysPager.setCurrentItem(day - 2, false)

            }
        }


        return binding.root
    }
}
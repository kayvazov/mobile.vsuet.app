package com.example.vsuet.startMenuFragment.scheduleMenuFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vsuet.databinding.ScheduleFragmentBinding
import com.example.vsuet.viewPagerAdapter.DaysViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class ScheduleFragment : Fragment() {


    private lateinit var binding: ScheduleFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScheduleFragmentBinding.inflate(inflater)
        var numerator =
            if (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1 % 2 == 1) {
                binding.scheduleNumeratorButton.text = "Числитель"
                0
            } else {
                binding.scheduleNumeratorButton.text = "Знаменатель"
                1
            }

        val days = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ")

        binding.apply {

            scheduleNumeratorButton.setOnClickListener {
                val currentItem = daysPager.currentItem
                numerator = if (numerator == 1) {
                    scheduleNumeratorButton.text = "Числитель"
                    0
                } else {
                    scheduleNumeratorButton.text = "Знаменатель"
                    1
                }


                daysPager.adapter =
                    DaysViewPagerAdapter(
                        requireActivity().supportFragmentManager,
                        lifecycle,
                        numerator
                    )
                daysPager.setCurrentItem(currentItem, false)
                TabLayoutMediator(daysTabLayout, daysPager) { tab, position ->
                    tab.text = days[position]
                }.attach()

            }

            daysPager.adapter =
                DaysViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle, numerator)
            TabLayoutMediator(daysTabLayout, daysPager) { tab, position ->
                tab.text = days[position]
            }.attach()

            return binding.root
        }
    }
}
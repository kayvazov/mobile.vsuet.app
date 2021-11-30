package com.example.vsuet.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment.TabLayoutFragment


class DaysViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val numerator: Boolean,
    private val data: List<LessonProperty>
) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = TabLayoutFragment(
            when (position) {
                1 -> "вторник"
                2 -> "среда"
                3 -> "четверг"
                4 -> "пятница"
                5 -> "суббота"
                6 -> "воскресенье"
                else -> "понедельник"
            },
            numerator,
            data
            )
        return fragment
    }

}
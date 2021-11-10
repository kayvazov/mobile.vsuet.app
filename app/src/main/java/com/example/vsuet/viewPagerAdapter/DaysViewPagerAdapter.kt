package com.example.vsuet.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment.TabLayoutFragment


class DaysViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val numerator: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = TabLayoutFragment(
            when (position) {
                1 -> "ВТОРНИК"
                2 -> "СРЕДА"
                3 -> "ЧЕТВЕРГ"
                4 -> "ПЯТНИЦА"
                5 -> "СУББОТА"
                else -> "ПОНЕДЕЛЬНИК"
            }, numerator
        )
        return fragment
    }

}
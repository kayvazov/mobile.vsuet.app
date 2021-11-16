package com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.databinding.TabLayoutFragmentBinding
import com.example.vsuet.lessonRecyclerView.LessonsRecyclerViewAdapter
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDataBase
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
        val lessonsDataBase = LessonDataBase.getInstance(requireContext())
        val lessonsDataSource = lessonsDataBase.lessonDataBaseDao

        viewModelFactory = TabLayoutViewModelFactory(application, lessonsDataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TabLayoutViewModel::class.java)
        val recyclerAdapter = LessonsRecyclerViewAdapter()

        val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> "вторник"
            Calendar.WEDNESDAY -> "среда"
            Calendar.THURSDAY -> "четверг"
            Calendar.FRIDAY -> "пятница"
            Calendar.SATURDAY -> "суббота"
            Calendar.SUNDAY -> "воскресенье"
            else -> "понедельник"
        }
        println(Calendar.getInstance().time)
        val personalAccountSettings =
            requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
        val groupName = personalAccountSettings.getString("groupNumber", "Не указана группа")!!
        val podGroup = personalAccountSettings.getString("underGroupNumber", "1")!!

        if (groupName != "Не указана группа") {
            recyclerAdapter.data =
                data.filter { it.day == day && it.weekType == numerator && it.subgroup == podGroup.toInt() }
        } else {
            binding.noGroupText.apply {
                visibility = View.VISIBLE
                alpha = 0f
                animate().alpha(1.0f).duration = 500
            }

        }




        binding.lessonsList.apply {
            alpha = 0f
            animate().alpha(1.0f).duration = 500
            adapter = recyclerAdapter
            binding.lessonsList.layoutManager = LinearLayoutManager(requireActivity())
        }
        return binding.root
    }

}
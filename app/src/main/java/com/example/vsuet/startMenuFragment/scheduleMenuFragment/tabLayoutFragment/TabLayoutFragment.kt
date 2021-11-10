package com.example.vsuet.startMenuFragment.scheduleMenuFragment.tabLayoutFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsuet.databinding.TabLayoutFragmentBinding
import com.example.vsuet.lessonRecyclerView.LessonsRecyclerViewAdapter
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDataBase
import java.util.*

class TabLayoutFragment(private val day: String?, private val numerator: Int) : Fragment() {

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
            Calendar.TUESDAY -> "Вторник"
            Calendar.WEDNESDAY -> "Среда"
            Calendar.THURSDAY -> "Четверг"
            Calendar.FRIDAY -> "Пятница"
            Calendar.SATURDAY -> "Суббота"
            Calendar.SUNDAY -> "Воскресенье"
            else -> "Понедельник"
        }
        val personalAccountSettings =
            requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
        val groupName = personalAccountSettings.getString("groupNumber", "LOL")!!
        val podGroup = personalAccountSettings.getString("underGroupNumber", "1")!!
        val groupLessons = viewModel.setGroupLessons(groupName, podGroup.toInt())
        if (day == dayOfWeek.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) {
            binding.noLessonsText.text = "Сегодня нет пар"
        } else {
            binding.noLessonsText.text = "В этот день нет пар"
        }
        groupLessons.observeForever { list ->
            recyclerAdapter.data =
                list.filter { it.lessonDay == day && numerator == it.numerator }
            if (recyclerAdapter.data.isEmpty()) {
                binding.noLessonsText.apply {
                    visibility = View.VISIBLE
                    alpha = 0f
                    animate().alpha(1.0f).duration = 500
                }
                binding.lessonsList.visibility = View.GONE
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
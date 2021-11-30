package com.example.vsuet.startMenuFragment.teachersMenuFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsuet.databinding.SearchFragmentBinding
import com.example.vsuet.listDialogFragment.ListDialogFragment
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDataBase
import com.example.vsuet.roomDataBase.lessonDataBase.LessonPair
import com.example.vsuet.teacherRecyclerView.TeacherRecyclerViewAdapter
import java.util.*


class SearchFragment :
    Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var viewModelFactory: SearchFragmentFactory
    private val args by navArgs<SearchFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater)
        val names = args.teacherNames.split(",").toMutableList()
        binding.apply {
            otherTeacherButton.text = names[0]
            val lessonDataBase = LessonDataBase.getInstance(requireActivity().applicationContext)
            val lessonDataSource = lessonDataBase.lessonDataBaseDao
            val application = requireNotNull(this@SearchFragment.activity).application
            viewModelFactory =
                SearchFragmentFactory(lessonDataSource, application)
            viewModel =
                ViewModelProvider(
                    this@SearchFragment,
                    viewModelFactory
                ).get(SearchFragmentViewModel::class.java)
            val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                Calendar.TUESDAY -> "Вторник"
                Calendar.WEDNESDAY -> "Среда"
                Calendar.THURSDAY -> "Четверг"
                Calendar.FRIDAY -> "Пятница"
                Calendar.SATURDAY -> "Суббота"
                Calendar.SUNDAY -> "Воскресенье"
                else -> "Понедельник"
            }

            otherDayButton.text = dayOfWeek


            var numerator =
                if (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1 % 2 == 1) {
                    numeratorSwitch.text = "Числитель"
                    numeratorSwitch.isChecked = false
                    0
                } else {
                    numeratorSwitch.text = "Знаменатель"
                    numeratorSwitch.isChecked = true
                    1
                }

            fun checkPair() {
                val teacherName: String = otherTeacherButton.text.toString()
                var pairTime = ""
                val teachersLessons =
                    viewModel.getTeachersLessons(
                        teacherName,
                        otherDayButton.text.toString()
                    )
                val recyclerAdapter = TeacherRecyclerViewAdapter()
                teachersLessons.observeForever { list ->
                    val listContainer =
                        list.sortedBy { it.lessonTime.split("-")[0].split(".")[0].toInt() }
                    val realList = mutableListOf<LessonPair>()
                    for (item in listContainer) {
                        if (realList.size == 0) {
                            realList.add(item)
                        } else if (item.lessonTime != realList.last().lessonTime) {
                            realList.add(item)
                        }
                    }
                    if (realList.any { it.numerator == numerator }) {
                        recyclerAdapter.data = realList.filter { it.numerator == numerator }
                        lessonTable.adapter = recyclerAdapter
                        tableContainer.visibility = View.VISIBLE
                        lessonTable.layoutManager = LinearLayoutManager(requireContext())
                    } else {
                        tableContainer.visibility = View.GONE
                    }


                    if (dayContainer.text.toString() == "Воскресенье" || (realList.filter{it.numerator == numerator}).isEmpty()) pairTime =
                        "Выходной"
                    apply {
                        val checkNum: Int =
                            if ((Calendar.getInstance()
                                    .get(Calendar.WEEK_OF_YEAR) - 1 % 2 == 1)
                            ) {
                                0
                            } else {
                                1
                            }
                        responseMessage.text = when (pairTime) {
                            "Выходной" -> {
                                responseMessage.visibility = View.VISIBLE
                                if (numerator == checkNum && dayOfWeek == otherDayButton.text.toString()) {
                                    "Сегодня у преподавателя нет пар"
                                } else {
                                    "В этот день у преподавателя нет пар"
                                }
                            }
                            else -> {
                                responseMessage.visibility = View.VISIBLE
                                "Расписание:"
                            }
                        }
                    }
                }
                otherDayButton.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        val dialog = ListDialogFragment(
                            "OtherDay",
                            numerator,
                            null,
                            false
                        )
                        dialog.show(requireActivity().supportFragmentManager, "OtherTeacherChoice")
                    }
                }
                otherTeacherButton.setOnClickListener {
                    val dialog = ListDialogFragment(
                        "Teacher",
                        numerator,
                        names,
                        true
                    )
                    dialog.show(requireActivity().supportFragmentManager, "OtherTeacherChoice")
                }
                numeratorSwitch.setOnClickListener {
                    if (numerator == 0) {
                        numerator = 1
                        checkPair()
                        numeratorSwitch.text = "Знаменатель"
                    } else {
                        numerator = 0
                        checkPair()
                        numeratorSwitch.text = "Числитель"
                    }
                }
            }

            checkPair()

            return root
        }
    }
}
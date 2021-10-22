package com.example.prepodsearch.searchFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prepodsearch.databinding.SearchFragmentBinding
import com.example.prepodsearch.listDialogFragment.ListDialogFragment
import com.example.prepodsearch.recyclerViewAdapter.LessonRecyclerViewAdapter
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonDataBase
import java.util.*


class SearchFragment :
    Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var viewModelFactory: SearchFragmentFactory
    private lateinit var marginTopToChange: ConstraintLayout.LayoutParams
    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater)
        val teacherName: String? = arguments?.getString("teacherName")
        val lessonDataBase = LessonDataBase.getInstance(requireActivity().applicationContext)
        val lessonDataSource = lessonDataBase.lessonDataBaseDao
        val application = requireNotNull(this.activity).application
        viewModelFactory = SearchFragmentFactory(lessonDataSource, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SearchFragmentViewModel::class.java)
        binding.apply {
            marginTopToChange =
                teacherNameContainer.layoutParams as ConstraintLayout.LayoutParams
            marginTopToChange.setMargins(0, 400, 0, 0)
            val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                Calendar.TUESDAY -> "Вторник"
                Calendar.WEDNESDAY -> "Среда"
                Calendar.THURSDAY -> "Четверг"
                Calendar.FRIDAY -> "Пятница"
                Calendar.SATURDAY -> "Суббота"
                Calendar.SUNDAY -> "Воскресенье"
                else -> "Понедельник"
            }
            dayContainer.text = dayOfWeek
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
            checkPair(numerator, teacherName!!)
            numeratorSwitch.setOnClickListener {
                if (numerator == 0) {
                    numerator = 1
                    checkPair(numerator, teacherName)
                    numeratorSwitch.text = "Знаменатель"
                } else {
                    numerator = 0
                    checkPair(numerator, teacherName)
                    numeratorSwitch.text = "Числитель"
                }
            }
            okButton.setOnClickListener {
                findNavController().navigate(SearchFragmentDirections.returnFromSearchResult())
            }
        }
        return binding.root
    }

    private fun checkPair(numerator: Int, teacherName: String) {
        binding.apply {
            val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                Calendar.TUESDAY -> "Вторник"
                Calendar.WEDNESDAY -> "Среда"
                Calendar.THURSDAY -> "Четверг"
                Calendar.FRIDAY -> "Пятница"
                Calendar.SATURDAY -> "Суббота"
                Calendar.SUNDAY -> "Воскресенье"
                else -> "Понедельник"
            }
            var pairTime = ""
            val teachersLessons =
                viewModel.getTeachersLessons(
                    teacherName,
                    dayContainer.text.toString()
                )
            val recyclerAdapter = LessonRecyclerViewAdapter()
            teachersLessons.observeForever { list ->
                val realList = list.toSet()
                    .filter { it.numerator == numerator }
                    .sortedBy { it.lessonTime.split("-")[0].split(".")[0] }

                if (realList.isNotEmpty()) {
                    if (realList.isNotEmpty()) {
                        recyclerAdapter.data = realList
                        lessonTable.adapter = recyclerAdapter
                        lessonTable.visibility = View.VISIBLE
                        lessonTable.layoutManager = LinearLayoutManager(requireContext())
                        marginTopToChange.setMargins(0, 70, 0, 0)
                        teacherNameContainer.layoutParams = marginTopToChange
                    }
                } else {
                    lessonTable.visibility = View.GONE
                    marginTopToChange.setMargins(0, 400, 0, 0)
                    teacherNameContainer.layoutParams = marginTopToChange

                }
                if (dayOfWeek == "Воскресенье" || realList.isEmpty()) pairTime =
                    "Выходной"
                apply {
                    val checkNum: Int =
                        if ((Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1 % 2 == 1)) {
                            0
                        } else {
                            1
                        }
                    responseMessage.text = when (pairTime) {
                        "Выходной" -> {
                            responseMessage.visibility = View.VISIBLE
                            if (numerator == checkNum && dayOfWeek == dayContainer.text.toString()) {
                                "Сегодня у преподавателя нет пар"
                            } else {
                                "В этот день у преподавателя нет пар"
                            }
                        }
                        else -> {
                            responseMessage.visibility = View.GONE
                            ""
                        }
                    }
                    "Преподаватель: \n$teacherName".also { teacherNameContainer.text = it }
                }
            }
            otherDayButton.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    val dialog = ListDialogFragment(
                        "OtherDay",
                        teacherName,
                        args.numerator,
                        null
                    )
                    dialog.show(requireActivity().supportFragmentManager, "OtherDayChoice")
                }
            }
        }
    }
}
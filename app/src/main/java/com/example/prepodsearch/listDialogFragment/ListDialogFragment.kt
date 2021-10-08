package com.example.prepodsearch.listDialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prepodsearch.R
import com.example.prepodsearch.recyclerViewAdapter.LessonRecyclerViewAdapter
import com.example.prepodsearch.databinding.ListDialogFragmentBinding
import com.example.prepodsearch.listViewAdapter.ListViewAdapter
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonDataBase
import com.example.prepodsearch.roomDataBase.teacherDataBase.Teacher
import com.example.prepodsearch.roomDataBase.teacherDataBase.TeacherDataBase
import java.util.*

class ListDialogFragment(
    private val type: String,
    private val faculty: String?,
    private val teacherName: String?
) :
    DialogFragment() {

    private lateinit var binding: ListDialogFragmentBinding
    private lateinit var viewModelFactory: ListViewModelFactory
    private lateinit var viewModel: ListDialogViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ListDialogFragmentBinding.inflate(layoutInflater)
        val adapter = ListViewAdapter()
        binding.listViewContainer.adapter = adapter

        fun getTeachersNames(teachers: List<Teacher>?): List<String> {

            val teachersNames = mutableListOf<String>()
            if (teachers != null) {
                for (teacher in teachers) {
                    teachersNames.add(teacher.teacherName!!)
                }
            }

            return teachersNames
        }

        val teacherDataBase = TeacherDataBase.getInstance(requireActivity().applicationContext)
        val teacherDataSource = teacherDataBase.teacherDataBaseDao
        val lessonDataBase = LessonDataBase.getInstance(requireActivity().applicationContext)
        val lessonDataSource = lessonDataBase.lessonDataBaseDao
        val application = requireNotNull(activity).application


        viewModelFactory = ListViewModelFactory(lessonDataSource, teacherDataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListDialogViewModel::class.java)
        adapter.data =
            if (type == "Faculty") {
                resources.getStringArray(R.array.Факультеты).toMutableList()
            } else {
                resources.getStringArray(R.array.daysOfTheWeek).toMutableList()
            }

        if (faculty != null) {
            val teacherData = viewModel.getFacultyTeachers(faculty)
            teacherData.observeForever {
                if (type == "Teacher") {
                    adapter.data = getTeachersNames(it)
                }
            }
        }

        binding.listViewContainer.setOnItemClickListener { _, _, position, _ ->

            val facultyText = requireActivity().findViewById<Button>(R.id.facultyContainer)
            val teacherText = requireActivity().findViewById<Button>(R.id.teacherContainer)
            val lessonsTable = requireActivity().findViewById<RecyclerView>(R.id.lessonTable)
            val responseMessage = requireActivity().findViewById<TextView>(R.id.responseMessage)

            val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                Calendar.TUESDAY -> "Вторник"
                Calendar.WEDNESDAY -> "Среда"
                Calendar.THURSDAY -> "Четверг"
                Calendar.FRIDAY -> "Пятница"
                Calendar.SATURDAY -> "Суббота"
                Calendar.SUNDAY -> "Воскресенье"
                else -> "Понедельник"
            }

            when (type) {
                "Teacher" -> teacherText.text = adapter.data[position].toString()
                "Faculty" -> {
                    facultyText.text = adapter.data[position].toString()
                    val teacherData = viewModel.getFacultyTeachers(facultyText.text.toString())
                    teacherData.observeForever {
                        if (it.isNotEmpty()) {
                            teacherText.text = it[0].teacherName
                        }
                    }
                }
                else -> {
                    val numerator =
                        if (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
                            0
                        } else {
                            1
                        }
                    val day = adapter.data[position].toString()
                    val teacherLessons =
                        viewModel.getTeachersLesson(teacherName!!, day, numerator)
                    teacherLessons.observeForever {
                        if (it.isNotEmpty()) {
                            val teacherNameContainer: TextView =
                                requireActivity().findViewById(R.id.teacherNameContainer)
                            val marginTopToChange =
                                teacherNameContainer.layoutParams as ConstraintLayout.LayoutParams
                            marginTopToChange.setMargins(0, 50, 0, 0)
                            val recyclerAdapter = LessonRecyclerViewAdapter()
                            lessonsTable.adapter = recyclerAdapter
                            lessonsTable.layoutManager = LinearLayoutManager(requireContext())
                            recyclerAdapter.data = it
                            responseMessage.visibility = View.GONE
                            lessonsTable.visibility = View.VISIBLE
                        } else {
                            responseMessage.text = "В этот день у преподавателя нет пар"
                            if (day == dayOfWeek) responseMessage.text =
                                "Сегодня у преподавателя нет пар"
                        }
                    }

                }
            }

            dialog?.dismiss()
        }



        return binding.root
    }


}
package com.example.prepodsearch.listDialogFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prepodsearch.R
import com.example.prepodsearch.databinding.ListDialogFragmentBinding
import com.example.prepodsearch.listViewAdapter.ListViewAdapter
import com.example.prepodsearch.recyclerViewAdapter.LessonRecyclerViewAdapter
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonDataBase
import com.example.prepodsearch.roomDataBase.teacherDataBase.TeacherDataBase
import java.util.*

class ListDialogFragment(
    private val type: String,
    private val teacherName: String?,
    private val response: String?,
    private val argNumerator: Int?,
    private val entries: MutableList<String>?
) :
    DialogFragment(){

    private lateinit var binding: ListDialogFragmentBinding
    private lateinit var viewModelFactory: ListViewModelFactory
    private lateinit var viewModel: ListDialogViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations= R.style.dialog_anim
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ListDialogFragmentBinding.inflate(layoutInflater)
        val adapter = ListViewAdapter()
        binding.listViewContainer.adapter = adapter

        val teacherDataBase = TeacherDataBase.getInstance(requireActivity().applicationContext)
        val teacherDataSource = teacherDataBase.teacherDataBaseDao
        val lessonDataBase = LessonDataBase.getInstance(requireActivity().applicationContext)
        val lessonDataSource = lessonDataBase.lessonDataBaseDao
        val application = requireNotNull(activity).application


        viewModelFactory = ListViewModelFactory(lessonDataSource, teacherDataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListDialogViewModel::class.java)


        when (type) {
            "OtherDay" -> {
                adapter.data =
                    resources.getStringArray(R.array.daysOfTheWeek).toMutableList()
            }

            "Teacher" -> {
                println("WTF")
                adapter.data = entries!!
                binding.teacherNameFilter.visibility = View.VISIBLE
                binding.teacherNameFilter.addTextChangedListener{
                    adapter.data = entries.filter { it.startsWith(binding.teacherNameFilter.text.toString(), true) }
                }

            }

            else -> {
                adapter.data = listOf()
            }
        }


        binding.listViewContainer.setOnItemClickListener { _, _, position, _ ->

            val teacherText = requireActivity().findViewById<Button>(R.id.teacherContainer)
            val lessonsTable = requireActivity().findViewById<RecyclerView>(R.id.lessonTable)
            val responseMessage = requireActivity().findViewById<TextView>(R.id.responseMessage)

            val dayContainer = requireActivity().findViewById<TextView>(R.id.dayContainer)
            val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                Calendar.TUESDAY -> {
                    "Вторник"
                }
                Calendar.WEDNESDAY -> {
                    "Среда"
                }
                Calendar.THURSDAY -> {
                    "Четверг"
                }
                Calendar.FRIDAY -> {
                    "Пятница"
                }
                Calendar.SATURDAY -> {
                    "Суббота"
                }
                Calendar.SUNDAY -> {
                    "Воскресенье"
                }
                else -> {
                    "Понедельник"
                }
            }


            val checkNum =
                if ((Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1 % 2 == 1)) {
                    0
                } else {
                    1
                }

            when (type) {
                "Teacher" -> teacherText.text = adapter.data[position].toString()
                else -> {
                    val day = adapter.data[position].toString()

                    dayContainer.text = day

                    val teacherLessons =
                        viewModel.getTeachersLesson(teacherName!!, day)
                    val teacherNameContainer: TextView =
                        requireActivity().findViewById(R.id.teacherNameContainer)
                    val marginTopToChange =
                        teacherNameContainer.layoutParams as ConstraintLayout.LayoutParams
                    teacherLessons.observeForever { list ->
                        if (list.any { it.numerator == argNumerator }) {
                            marginTopToChange.setMargins(0, 70, 0, 0)
                            val recyclerAdapter = LessonRecyclerViewAdapter()
                            lessonsTable.adapter = recyclerAdapter
                            lessonsTable.layoutManager =
                                LinearLayoutManager(requireContext())
                            recyclerAdapter.data =
                                list.filter { it.numerator == argNumerator }
                            if (day == dayOfWeek && argNumerator == checkNum) {
                                responseMessage.text = response
                            } else {
                                responseMessage.visibility = View.GONE
                            }
                            lessonsTable.visibility = View.VISIBLE
                        } else {
                            marginTopToChange.setMargins(0, 400, 0, 0)
                            responseMessage.visibility = View.VISIBLE
                            lessonsTable.visibility = View.GONE
                            responseMessage.text = "В этот день у преподавателя нет пар"
                            if (day == dayOfWeek && argNumerator == checkNum) responseMessage.text =
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
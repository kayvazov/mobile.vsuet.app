package com.example.vsuet.listDialogFragment

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
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
import com.example.vsuet.R
import com.example.vsuet.databinding.ListDialogFragmentBinding
import com.example.vsuet.listViewAdapter.ListViewAdapter
import com.example.vsuet.roomDataBase.lessonDataBase.LessonDataBase
import com.example.vsuet.roomDataBase.lessonDataBase.LessonPair
import com.example.vsuet.teacherRecyclerView.TeacherRecyclerViewAdapter
import java.util.*

class ListDialogFragment(
    private val type: String,
    private val argNumerator: Int?,
    private val entries: MutableList<String>?,
    private val toChange: Boolean
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
        val lessonsTable = requireActivity().findViewById<RecyclerView>(R.id.lessonTable)
        val tableContainer = requireActivity().findViewById<ConstraintLayout>(R.id.tableContainer)
        val responseMessage = requireActivity().findViewById<TextView>(R.id.responseMessage)
        val teacherChanger = requireActivity().findViewById<Button>(R.id.otherTeacherButton)
        val dayContainer = requireActivity().findViewById<Button>(R.id.otherDayButton)

        fun checkPair(numerator: Int, teacherName: String) {

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
                viewModel.getTeachersLesson(
                    teacherName,
                    dayContainer.text.toString()
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
                        println(item.lessonTime)
                        println(realList.last().lessonTime)
                        realList.add(item)
                    }
                }
                if (realList.isNotEmpty()) {
                    recyclerAdapter.data = realList
                    lessonsTable.adapter = recyclerAdapter
                    tableContainer.visibility = View.VISIBLE
                    responseMessage.visibility = View.VISIBLE
                    lessonsTable.layoutManager = LinearLayoutManager(requireContext())
                } else {
                    tableContainer.visibility = View.GONE
                }
                if (dayContainer.text.toString() == "Воскресенье" || realList.isEmpty()) pairTime =
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
                            responseMessage.visibility = View.VISIBLE
                            "Расписание:"
                        }
                    }
                }
            }
        }

        binding.apply {
            listViewContainer.adapter = adapter
            val lessonDataBase = LessonDataBase.getInstance(requireActivity().applicationContext)
            val lessonDataSource = lessonDataBase.lessonDataBaseDao
            val application = requireNotNull(activity).application
            dialog?.window?.attributes?.windowAnimations = R.style.dialog_anim
            viewModelFactory = ListViewModelFactory(lessonDataSource, application)
            viewModel =
                ViewModelProvider(
                    this@ListDialogFragment,
                    viewModelFactory
                ).get(ListDialogViewModel::class.java)
            when (type) {
                "OtherDay" -> {
                    adapter.data =
                        resources.getStringArray(R.array.daysOfTheWeek).toMutableList()
                }
                "Teacher" -> {
                    adapter.data = entries!!
                    println(adapter.data)
                    teacherNameFilter.visibility = View.VISIBLE
                    teacherNameFilter.post {
                        val itemHeight = teacherNameFilter.height
                        listViewContainer.setPadding(0, 0, 0, itemHeight)
                        teacherNameFilter.addTextChangedListener {
                            adapter.data = entries.filter {
                                it.startsWith(
                                    teacherNameFilter.text.toString(),
                                    true
                                )
                            }
                            if (adapter.count * itemHeight <= listDialogContainer.height) {
                                listViewContainer.setPadding(0, 0, 0, 0)
                            } else {
                                listViewContainer.setPadding(0, 0, 0, itemHeight)
                            }
                        }
                    }
                }
                "Settings" -> {
                    adapter.data = entries!!
                    teacherNameFilter.visibility = View.VISIBLE
                    teacherNameFilter.hint = SpannableStringBuilder("Номер группы")
                    teacherNameFilter.addTextChangedListener {
                        adapter.data = entries.filter {
                            it.startsWith(
                                teacherNameFilter.text.toString(),
                                true
                            )
                        }
                    }
                }

                else -> {
                    adapter.data = listOf()
                }
            }
            listViewContainer.setOnItemClickListener { _, _, position, _ ->
                when (type) {
                    "Teacher" -> {
                        if (toChange) {
                            checkPair(
                                argNumerator!!,
                                adapter.data[position].toString()
                            )
                            teacherChanger.text = adapter.data[position].toString()
                        }
                    }
                    "Settings" -> {
                        requireActivity().findViewById<Button>(R.id.groupTextButton).text =
                            adapter.data[position].toString()
                        val settingsEditor = requireActivity().getSharedPreferences(
                            "accountSettings",
                            Context.MODE_PRIVATE
                        ).edit()
                        settingsEditor.putString("groupNumber", adapter.data[position].toString())
                            .apply()
                        settingsEditor.putBoolean("isGroupChanged", true).apply()
                    }
                    else -> {
                        val day = adapter.data[position].toString()
                        dayContainer.text = day
                        checkPair(argNumerator!!, teacherChanger.text.toString())
                    }
                }
                dialog?.dismiss()
            }
            return root
        }
    }
}
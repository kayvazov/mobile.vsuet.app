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
import com.example.vsuet.databinding.ListDialogFragmentForDaysBinding
import com.example.vsuet.listViewAdapter.ListViewAdapter
import com.example.vsuet.roomDataBase.repository.RepositoryDataBase
import com.example.vsuet.startMenuFragment.teachersMenuFragment.SearchFragmentFactory
import com.example.vsuet.startMenuFragment.teachersMenuFragment.SearchFragmentViewModel
import com.example.vsuet.teacherRecyclerView.TeacherRecyclerViewAdapter
import java.util.*

class ListDialogFragment(
    private val type: String,
    private val argNumerator: Boolean?,
    private val entries: MutableList<String>?
) :
    DialogFragment() {

    private lateinit var bindingTeachers: ListDialogFragmentBinding
    private lateinit var bindingDays: ListDialogFragmentForDaysBinding
    private lateinit var viewModelFactory: SearchFragmentFactory
    private lateinit var viewModel: SearchFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingTeachers = ListDialogFragmentBinding.inflate(inflater)
        bindingDays = ListDialogFragmentForDaysBinding.inflate(inflater)
        val adapter = ListViewAdapter()
        val lessonsTable = requireActivity().findViewById<RecyclerView>(R.id.lessonTable)
        val tableContainer = requireActivity().findViewById<ConstraintLayout>(R.id.tableContainer)
        val responseMessage = requireActivity().findViewById<TextView>(R.id.responseMessage)
        val teacherChanger = requireActivity().findViewById<Button>(R.id.otherTeacherButton)
        val dayContainer = requireActivity().findViewById<Button>(R.id.otherDayButton)

        fun checkPair(numerator: Boolean, teacherName: String) {

            val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                Calendar.TUESDAY -> "вторник"
                Calendar.WEDNESDAY -> "среда"
                Calendar.THURSDAY -> "четверг"
                Calendar.FRIDAY -> "пятница"
                Calendar.SATURDAY -> "суббота"
                Calendar.SUNDAY -> "воскресенье"
                else -> "понедельник"
            }
            var pairTime = ""
            val recyclerAdapter = TeacherRecyclerViewAdapter()
            val isDataBaseCreated = requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
                .getBoolean("isTeacherLessonsDBCreated", false)
            println(isDataBaseCreated)
            viewModel.getTeachersLessons(teacherName, isDataBaseCreated)
            viewModel.teacherLessons.observeForever { list ->
                if (list.any {
                        it.day == dayContainer.text.toString()
                            .lowercase(Locale.getDefault())
                    }) {
                    recyclerAdapter.data = list.filter {
                        it.day == dayContainer.text.toString()
                            .lowercase(Locale.getDefault()) && it.weekType == numerator
                    }.toSet().toList()
                    println("DATA")
                    println(recyclerAdapter.data)
                    lessonsTable.adapter = recyclerAdapter
                    lessonsTable.layoutManager = LinearLayoutManager(requireContext())
                    tableContainer.visibility = View.VISIBLE
                    responseMessage.visibility = View.VISIBLE
                } else {
                    tableContainer.visibility = View.GONE
                }
                if (dayContainer.text.toString() == "Воскресенье" || list.isEmpty()) pairTime =
                    "Выходной"
                apply {
                    val checkNum: Boolean =
                        (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1 % 2 == 1)
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

        val application = requireNotNull(activity).application
        val repository = RepositoryDataBase.getInstance(application)
        val repositoryDataSource = repository.repositoryDao
        dialog?.window?.attributes?.windowAnimations = R.style.dialog_anim
        viewModelFactory = SearchFragmentFactory(repositoryDataSource, application)
        viewModel =
            ViewModelProvider(
                this@ListDialogFragment,
                viewModelFactory
            )[SearchFragmentViewModel::class.java]

        if(type == "Teacher" || type == "Settings") {
            bindingTeachers.apply {
                listViewContainer.adapter = adapter
                when (type) {
                    "Teacher" -> {
                        adapter.data = entries!!
                        teacherNameFilter.visibility = View.VISIBLE
                        teacherNameFilter.post {
                            teacherNameFilter.addTextChangedListener {
                                adapter.data = entries.filter {
                                    it.startsWith(
                                        teacherNameFilter.text.toString(),
                                        true
                                    )
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
                            checkPair(
                                argNumerator!!,
                                adapter.data[position].toString()
                            )
                            teacherChanger.text = adapter.data[position].toString()
                        }
                        "Settings" -> {
                            requireActivity().findViewById<Button>(R.id.groupTextButton).text =
                                adapter.data[position].toString()
                            val settingsEditor = requireActivity().getSharedPreferences(
                                "accountSettings",
                                Context.MODE_PRIVATE
                            ).edit()
                            settingsEditor.putString(
                                "groupNumber",
                                adapter.data[position].toString()
                            )
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
        } else {
            bindingDays.apply {

                adapter.data =
                    resources.getStringArray(R.array.daysOfTheWeek).toMutableList()

                listViewContainer.adapter = adapter
                listViewContainer.setOnItemClickListener { _, _, position, _ ->
                    val day = adapter.data[position].toString()
                    dayContainer.text = day
                    checkPair(argNumerator!!, teacherChanger.text.toString())
                    dialog?.dismiss()
                }


                return root
            }
        }
    }
}
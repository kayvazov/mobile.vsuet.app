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
import com.example.vsuet.roomDataBase.repository.RepositoryDataBase
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
            val application = requireNotNull(this@SearchFragment.activity).application
            val repository = RepositoryDataBase.getInstance(application)
            val repositoryDataSource = repository.repositoryDao
            viewModelFactory =
                SearchFragmentFactory(repositoryDataSource, application)
            viewModel =
                ViewModelProvider(
                    this@SearchFragment,
                    viewModelFactory
                )[SearchFragmentViewModel::class.java]
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
                    true
                } else {
                    numeratorSwitch.text = "Знаменатель"
                    numeratorSwitch.isChecked = true
                    false
                }

            fun checkPair() {
                viewModel.getTeachersLessons(otherTeacherButton.text.toString())
                var pairTime = ""
                val recyclerAdapter = TeacherRecyclerViewAdapter()
                viewModel.teacherLessons.observe(viewLifecycleOwner) { list ->
                    println(list)
                    println(
                        dayContainer.text.toString()
                            .lowercase(Locale.getDefault())
                    )
                    if (list.any {
                            it.weekType == numerator && it.day == otherDayButton.text.toString()
                                .lowercase(Locale.getDefault())
                        }) {
                        println(list)
                        recyclerAdapter.data = list.filter {
                            it.weekType == numerator && it.day == otherDayButton.text.toString()
                                .lowercase(Locale.getDefault())
                        }.toSet().toList()
                        lessonTable.adapter = recyclerAdapter
                        tableContainer.visibility = View.VISIBLE
                        lessonTable.layoutManager = LinearLayoutManager(requireContext())
                    } else {
                        tableContainer.visibility = View.GONE
                    }
                    if (dayContainer.text.toString() == "Воскресенье" || (list.filter { it.weekType == numerator }).isEmpty()) pairTime =
                        "Выходной"
                    apply {
                        val checkNum: Boolean =
                            (Calendar.getInstance()
                                .get(Calendar.WEEK_OF_YEAR) - 1 % 2 == 1)
                        responseMessage.text = when (pairTime) {
                            "Выходной" -> {
                                responseMessage.visibility = View.VISIBLE
                                if (numerator == checkNum && dayOfWeek == otherDayButton.text.toString()
                                        .lowercase(Locale.getDefault())
                                ) {
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
                            null
                        )
                        dialog.show(requireActivity().supportFragmentManager, "OtherTeacherChoice")
                    }
                }
                otherTeacherButton.setOnClickListener {
                    val dialog = ListDialogFragment(
                        "Teacher",
                        numerator,
                        names
                    )
                    dialog.show(requireActivity().supportFragmentManager, "OtherTeacherChoice")
                }
                numeratorSwitch.setOnClickListener {
                    if (numerator) {
                        numerator = false
                        checkPair()
                        numeratorSwitch.text = "Знаменатель"
                    } else {
                        numerator = true
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
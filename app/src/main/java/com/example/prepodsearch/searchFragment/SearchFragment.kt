package com.example.prepodsearch.searchFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonPair
import com.example.prepodsearch.roomDataBase.teacherDataBase.TeacherDataBase
import java.text.SimpleDateFormat
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

        val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> "Вторник"
            Calendar.WEDNESDAY -> "Среда"
            Calendar.THURSDAY -> "Четверг"
            Calendar.FRIDAY -> "Пятница"
            Calendar.SATURDAY -> "Суббота"
            Calendar.SUNDAY -> "Воскресенье"
            else -> "Понедельник"
        }


        binding = SearchFragmentBinding.inflate(inflater)
        var teacherName: String? = arguments?.getString("teacherName")
        val lessonDataBase = LessonDataBase.getInstance(requireActivity().applicationContext)
        val lessonDataSource = lessonDataBase.lessonDataBaseDao
        val teacherDataBase = TeacherDataBase.getInstance(requireActivity().applicationContext)
        val teacherDataSource = teacherDataBase.teacherDataBaseDao
        val application = requireNotNull(this.activity).application
        viewModelFactory = SearchFragmentFactory(lessonDataSource, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SearchFragmentViewModel::class.java)


        marginTopToChange =
            binding.teacherNameContainer.layoutParams as ConstraintLayout.LayoutParams
        marginTopToChange.setMargins(0, 400, 0, 0)

        var numerator: Int


        binding.apply {
            dayContainer.text = dayOfWeek
            numerator =
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

        }


        binding.okButton.setOnClickListener {
            findNavController().navigate(SearchFragmentDirections.returnFromSearchResult())
        }



        return binding.root
    }

    private fun timeComparator(firstTime: List<String>, secondTime: List<String>): Boolean {

        return if (firstTime[0] != "" && secondTime[0] != "" && firstTime[0] != secondTime[0]) {
            firstTime[0].toInt() > secondTime[0].toInt()
        } else {
            if (firstTime[1] != "" && secondTime[1] != "") {
                firstTime[1].toInt() >= secondTime[1].toInt()
            } else {
                if (firstTime[1] == "" && secondTime[1] == "") {
                    true
                } else firstTime[1] != ""
            }
        }


    }

    @SuppressLint("SimpleDateFormat")
    private fun checkPair(numerator: Int, teacherName: String) {


        val timeFormatter = SimpleDateFormat("HH:mm")
        val time = timeFormatter.format(Calendar.getInstance().time)
        val hoursMinutes = time.split(":")

        val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> "Вторник"
            Calendar.WEDNESDAY -> "Среда"
            Calendar.THURSDAY -> "Четверг"
            Calendar.FRIDAY -> "Пятница"
            Calendar.SATURDAY -> "Суббота"
            Calendar.SUNDAY -> "Воскресенье"
            else -> "Понедельник"
        }


        var pairTime: String
        val teachersLessons =
            viewModel.getTeachersLessons(
                teacherName,
                binding.dayContainer.text.toString()
            )
        var lessonsEnd = ""
        var lessonsStart = ""
        var currentLesson: LessonPair? = null
        val recyclerAdapter = LessonRecyclerViewAdapter()

        teachersLessons.observeForever { list ->
            val realList = list.toSet()
                .filter { it.numerator == numerator }
                .sortedBy { it.lessonTime.split("-")[0].split(".")[0] }
            pairTime = if (timeComparator(hoursMinutes, "8:00".split(":")) && timeComparator(
                    "9:45".split(":"),
                    hoursMinutes
                )
            ) {
                "8:00-9:45"
            } else if (timeComparator(
                    hoursMinutes,
                    "9:55".split(":")
                ) && timeComparator("11:20".split(":"), hoursMinutes)
            ) {
                "9:55-11:20"
            } else if (timeComparator(hoursMinutes, "11:50".split(":")) && timeComparator(
                    "13:25".split(
                        ":"
                    ), hoursMinutes
                )
            ) {
                "11:50-13:25"
            } else if (timeComparator(hoursMinutes, "13:35".split(":")) && timeComparator(
                    "15:10".split(
                        ":"
                    ), hoursMinutes
                )
            ) {
                "13:35-15:10"
            } else if (timeComparator(hoursMinutes, "15:20".split(":")) && timeComparator(
                    "16:55".split(
                        ":"
                    ), hoursMinutes
                )
            ) {
                "15:20-16:55"
            } else if (timeComparator(hoursMinutes, "17:05".split(":")) && timeComparator(
                    "18:40".split(
                        ":"
                    ), hoursMinutes
                )
            ) {
                "17:05-18:40"
            } else if (timeComparator(hoursMinutes, "18:50".split(":")) && timeComparator(
                    "20:25".split(
                        ":"
                    ), hoursMinutes
                )
            ) {
                "18:50-20:25"
            } else "Перерыв"


            if (realList.isNotEmpty()) {
                lessonsEnd = realList.last().lessonTime.split("-").last()
                lessonsStart = realList.first().lessonTime.split("-").first()
                val leftLessons = mutableListOf<LessonPair>()
                val nextPairCheck = realList.size
                if (realList.isNotEmpty()) {

                    for (lessons in 0 until nextPairCheck) {
                        if (realList[lessons].lessonTime.split(".")[0] == pairTime.split(":")[0]) currentLesson =
                            realList[lessons]
                        if (timeComparator(
                                realList[lessons].lessonTime.split("-")[1].split("."),
                                hoursMinutes
                            )
                        ) {
                            leftLessons.add(realList[lessons])
                        }
                    }

                    if (pairTime == "Перерыв" && realList.isNotEmpty()) {
                        for (lessons in 0 until nextPairCheck - 1) {
                            if (timeComparator(
                                    hoursMinutes,
                                    realList[lessons].lessonTime.split("-")[1].split(".")
                                ) && timeComparator(
                                    realList[lessons + 1].lessonTime.split("-")[0].split("."),
                                    hoursMinutes
                                )
                            ) {
                                currentLesson = realList[lessons + 1]
                            }
                        }

                    }

                    binding.apply {
                        if (leftLessons.isNotEmpty()) {
                            recyclerAdapter.data = leftLessons
                            lessonTable.visibility = View.VISIBLE
                            lessonTable.adapter = recyclerAdapter
                            lessonTable.layoutManager = LinearLayoutManager(requireContext())
                            marginTopToChange.setMargins(0, 70, 0, 0)
                            teacherNameContainer.layoutParams = marginTopToChange
                            println("?")
                        } else if (realList.isNotEmpty()) {
                            recyclerAdapter.data = realList
                            lessonTable.adapter = recyclerAdapter
                            lessonTable.visibility = View.VISIBLE
                            lessonTable.layoutManager = LinearLayoutManager(requireContext())
                            marginTopToChange.setMargins(0, 70, 0, 0)
                            teacherNameContainer.layoutParams = marginTopToChange
                        }

                    }

                }


            } else {
                binding.apply {
                    lessonTable.visibility = View.GONE
                    marginTopToChange.setMargins(0, 400, 0, 0)
                    teacherNameContainer.layoutParams = marginTopToChange
                }
            }
            if (lessonsEnd != "" && timeComparator(time.split(":"), lessonsEnd.split("."))) {
                pairTime = "Рабочий день окончен"
            }
            if (lessonsStart != "" && timeComparator(lessonsStart.split("."), time.split(":"))) {
                pairTime = "Рабочий день не начался"
            }


            val lessonTime = currentLesson?.lessonTime
            val lessonName = currentLesson?.lessonName
            val lessonClass = currentLesson?.lessonClass
            println(currentLesson)


            if (dayOfWeek == "Воскресенье" || realList.isEmpty()) pairTime =
                "Выходной"

            println(pairTime)

            binding.apply {


                println(dayContainer.text.toString())
                val checkNum: Int =
                    if ((Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1 % 2 == 1)) {
                        0
                    } else {
                        1
                    }
                responseMessage.text = when (pairTime) {
                    "Перерыв" -> {
                        responseMessage.visibility = View.GONE
                        ""
                    }
                    "Рабочий день не начался" -> if (numerator == checkNum && dayOfWeek == dayContainer.text.toString()) {

                        responseMessage.visibility = View.GONE
                        ""
                    } else {
                        responseMessage.visibility = View.GONE
                        ""
                    }
                    "Рабочий день окончен" -> if (numerator == checkNum && dayOfWeek == dayContainer.text.toString()) {
                        responseMessage.visibility = View.GONE
                        ""
                    } else {
                        responseMessage.visibility = View.GONE
                        ""
                    }
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





        binding.otherDayButton.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                val dialog = ListDialogFragment(
                    "OtherDay",
                    teacherName,
                    binding.responseMessage.text.toString(),
                    args.numerator,
                    null
                )
                dialog.show(requireActivity().supportFragmentManager, "OtherDayChoice")
            }
        }


    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

}
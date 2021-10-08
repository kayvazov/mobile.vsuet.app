package com.example.prepodsearch.searchFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.prepodsearch.recyclerViewAdapter.LessonRecyclerViewAdapter
import com.example.prepodsearch.databinding.SearchFragmentBinding
import com.example.prepodsearch.listDialogFragment.ListDialogFragment
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonDataBase
import com.example.prepodsearch.roomDataBase.lessonDataBase.LessonPair
import java.text.SimpleDateFormat
import java.util.*


class SearchFragment :
    Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var viewModelFactory: SearchFragmentFactory
    private lateinit var marginTopToChange: ConstraintLayout.LayoutParams

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = SearchFragmentBinding.inflate(inflater)

        val lessonDataBase = LessonDataBase.getInstance(requireActivity().applicationContext)
        val lessonDataSource = lessonDataBase.lessonDataBaseDao
        val application = requireNotNull(this.activity).application
        viewModelFactory = SearchFragmentFactory(lessonDataSource, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SearchFragmentViewModel::class.java)


        marginTopToChange =
            binding.teacherNameContainer.layoutParams as ConstraintLayout.LayoutParams
        marginTopToChange.setMargins(0, 250, 0, 0)


        checkPair()


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
    private fun checkPair() {

        val timeFormatter = SimpleDateFormat("HH:MM")
        val time = timeFormatter.format(Calendar.getInstance().time)
        val hoursMinutes = time.split(":")

        val teacherName: String? = arguments?.getString("teacherName")
        val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> "Вторник"
            Calendar.WEDNESDAY -> "Среда"
            Calendar.THURSDAY -> "Четверг"
            Calendar.FRIDAY -> "Пятница"
            Calendar.SATURDAY -> "Суббота"
            Calendar.SUNDAY -> "Воскресенье"
            else -> "Понедельник"
        }

        val numerator = if (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
            0
        } else {
            1
        }
        var pairTime: String
        val teachersLessons =
            viewModel.getTeachersLessons(teacherName.toString(), dayOfWeek, numerator)
        var lessonsEnd = ""
        var lessonsStart = ""
        teachersLessons.observeForever {

            if (it.isNotEmpty()) {
                lessonsEnd = it.last().lessonTime.split("-").last()
                lessonsStart = it.first().lessonTime.split("-").first()
            }
            if (dayOfWeek == "Воскресенье" || it.isEmpty()) pairTime = "Выходной"
        }



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


        var currentLesson: LessonPair? = null

        val recyclerAdapter = LessonRecyclerViewAdapter()

        teachersLessons.observeForever {
            if (it.isNotEmpty()) {
                lessonsEnd = it.last().lessonTime.split("-").last()
                lessonsStart = it.first().lessonTime.split("-").first()
                val leftLessons = mutableListOf<LessonPair>()
                val nextPairCheck = it.size
                if (pairTime == "Перерыв" && it.isNotEmpty()) {
                    for (lessons in 1..nextPairCheck) {
                        if (it[lessons].lessonTime.split("-")[1] < time && time < it[lessons + 1].lessonTime.split(
                                "-"
                            )[0]
                        ) {
                            currentLesson = it[lessons + 1]
                        }
                    }
                } else if (it.isNotEmpty()) {

                    // ВАЖНОЕ МЕСТО, ВОЗМОЖНО, НУЖНО ПОМЕНЯТЬ
                    for (lessons in 0..nextPairCheck) {
                        if (it[lessons].lessonTime == pairTime) currentLesson =
                            it[lessons]
                        if (it[lessons].lessonTime.split("-")[1] > time) {
                            leftLessons.add(it[lessons])
                        }
                    }
                    binding.apply {
                        lessonTable.visibility = View.VISIBLE
                        leftLessonsText.visibility = View.VISIBLE
                        marginTopToChange.setMargins(0, 0, 0, 0)
                        responseMessage.layoutParams = marginTopToChange
                    }
                    recyclerAdapter.data = leftLessons
                }
                if (dayOfWeek == "Воскресенье" || it.isEmpty()) pairTime = "Выходной"
            }
            if (dayOfWeek == "Воскресенье" || it.isEmpty()) pairTime =
                "Выходной"

        }



        if (timeComparator(time.split(":"), lessonsEnd.split(""))) {
            pairTime = "Рабочий день окончен"
        }
        if (timeComparator(lessonsStart.split(""), time.split(":"))) {
            pairTime = "Рабочий день не начался"
        }


        val lessonTime = currentLesson?.lessonTime
        val lessonName = currentLesson?.lessonName
        val lessonClass = currentLesson?.lessonClass

        binding.apply {
            responseMessage.text = when (pairTime) {
                "Перерыв" -> "Сейчас перерыв, следующая пара преподавателя - $lessonName, она пройдёт в $lessonClass аудитории, время проведения - $lessonTime"
                "Рабочий день не начался" -> "Рабочий день ещё не начался"
                "Рабочий день окончен" -> "Рабочий день окончен"
                "Выходной" -> "Сегодня у преподавателя нет пар"
                else -> "Сейчас $teacherName ведёт $lessonName в $lessonClass аудитории, время проведения - $lessonTime"
            }

            "Преподаватель: \n$teacherName".also { teacherNameContainer.text = it }


        }



        binding.otherDayButton.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                val dialog = ListDialogFragment("OtherDay", null, teacherName)
                dialog.show(requireActivity().supportFragmentManager, "OtherDayChoice")
            }
        }


    }

}
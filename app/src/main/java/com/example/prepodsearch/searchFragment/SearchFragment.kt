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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prepodsearch.RecyclerViewAdapter.LessonRecyclerViewAdapter
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
        marginTopToChange.setMargins(0, 350, 0, 0)
        binding.teacherNameContainer.layoutParams = marginTopToChange

        checkPair()


        binding.okButton.setOnClickListener {
            findNavController().navigate(SearchFragmentDirections.returnFromSearchResult())
        }


        return binding.root
    }

    private fun timeComparator(firstTime: List<String>, secondTime: List<String>): Boolean {

        return when {
            firstTime[0].toInt() >= secondTime[0].toInt() -> true
            firstTime[0].toInt() == secondTime[0].toInt() -> firstTime[1].toInt() >= secondTime[1].toInt()
            else -> false
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

        val numerator = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) % 2 == 0
        val teachersLessons =
            viewModel.getTeachersLessons(teacherName.toString(), dayOfWeek, numerator)
        val lessonsEnd = teachersLessons.value?.last()?.lessonTime?.split("-")?.last()
        val lessonsStart = teachersLessons.value?.first()?.lessonTime?.split("-")?.first()

        var pairTime: String

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

        if (lessonsEnd != null && timeComparator(time.split(":"), lessonsEnd.split(""))) {
            pairTime = "Рабочий день закончен"
        }
        if (lessonsStart != null && timeComparator(lessonsStart.split(""), time.split(":"))) {
            pairTime = "Рабочий день ещё не начался"
        }

        if (dayOfWeek == "Воскресенье" || teachersLessons.value == null) pairTime = "Выходной"


        var currentLesson: LessonPair? = null
        val nextPairCheck = teachersLessons.value?.size
        val recyclerAdapter = LessonRecyclerViewAdapter()
        val leftLessons = mutableListOf<LessonPair>()
        if (pairTime == "Перерыв" && teachersLessons.value != null) {
            for (lessons in 1..nextPairCheck!!) {
                if (teachersLessons.value!![lessons].lessonTime.split("-")[1] < time && time < teachersLessons.value!![lessons + 1].lessonTime.split(
                        "-"
                    )[0]
                ) {
                    currentLesson = teachersLessons.value!![lessons + 1]
                }
            }
        } else if (teachersLessons.value != null) {

            // ВАЖНОЕ МЕСТО, ВОЗМОЖНО, НУЖНО ПОМЕНЯТЬ

            binding.lessonTable.apply {
                visibility = View.VISIBLE
                adapter = recyclerAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            for (lessons in 0..nextPairCheck!!) {
                if (teachersLessons.value!![lessons].lessonTime == pairTime) currentLesson =
                    teachersLessons.value!![lessons]
                if (teachersLessons.value!![lessons].lessonTime.split("-")[1] > time) {
                    leftLessons.add(teachersLessons.value!![lessons])
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
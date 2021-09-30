package com.example.prepodsearch.searchDialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.prepodsearch.RoomDataBase.LessonDataBase.LessonPair
import com.example.prepodsearch.databinding.FragmentSearchDialogBinding


class SearchDialogFragment(val lessonPair: LessonPair?, val responseInfo: String) : DialogFragment() {

    private lateinit var binding: FragmentSearchDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchDialogBinding.inflate(inflater)

        val lessonTime = lessonPair?.lessonTime
        val teacherName = lessonPair?.lessonTeacher
        val lessonName = lessonPair?.lessonName
        val lessonClass = lessonPair?.lessonClass

        binding.responseMessage.text = when(responseInfo){
            "Перерыв" -> "Сейчас перерыв, следующая пара преподавателя - $lessonName, она пройдёт в $lessonClass аудитории, время проведения - $lessonTime"
            "Рабочий день не начался" -> "Рабочий день ещё не начался"
            "Рабочий день окончен" -> "Рабочий день окончен"
            else -> "Сейчас $teacherName ведёт $lessonName в $lessonClass аудитории, время проведения - $lessonTime"
        }


        binding.okButton.setOnClickListener {
            this.dismiss()
        }


        return binding.root
    }

}
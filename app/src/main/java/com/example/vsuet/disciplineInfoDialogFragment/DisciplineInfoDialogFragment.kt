package com.example.vsuet.disciplineInfoDialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.vsuet.API.InformationItem
import com.example.vsuet.R
import com.example.vsuet.databinding.DisciplineInfoDialogFragmentBinding

class DisciplineInfoDialogFragment(private val lessonInfo: InformationItem) : DialogFragment() {

    private lateinit var binding: DisciplineInfoDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DisciplineInfoDialogFragmentBinding.inflate(layoutInflater)
        dialog?.window?.attributes?.windowAnimations = R.style.dialog_anim

        binding.apply {
            department.text = "Кафедра - " + lessonInfo.department
            teacher.text = "Преподаватель - " + lessonInfo.teacher
            updatedTime.text = "Дата изменения - " + lessonInfo.updatedAt
            hoursCount.text = "Количество часов - " + lessonInfo.hours
            semester.text = "Семестр - " + lessonInfo.semester
        }

        return binding.root
    }

}
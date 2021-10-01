package com.example.prepodsearch.listDialogFragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.prepodsearch.R
import com.example.prepodsearch.databinding.ListDialogFragmentBinding
import com.example.prepodsearch.listViewAdapter.ListViewAdapter
import com.example.prepodsearch.roomDataBase.teacherDataBase.Teacher
import com.example.prepodsearch.roomDataBase.teacherDataBase.TeacherDataBase

class ListDialogFragment(private val type: String, private val faculty: String?) :
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
                    teachersNames.add(teacher.teacherName)
                }
            }

            return teachersNames
        }

        val teacherDataBase = TeacherDataBase.getInstance(requireActivity().applicationContext)
        val teacherDataSource = teacherDataBase.teacherDataBaseDao
        val application = requireNotNull(activity).application

        viewModelFactory = ListViewModelFactory(teacherDataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListDialogViewModel::class.java)
        adapter.data =
            if (type == "Teacher" && viewModel.getFacultyTeachers(faculty!!).value != null) {

                getTeachersNames(viewModel.getFacultyTeachers(faculty).value!!)


            } else {
                resources.getStringArray(R.array.Факультеты).toMutableList()
            }

        binding.listViewContainer.setOnItemClickListener { _, _, position, _ ->

            val facultyText = requireActivity().findViewById<TextView>(R.id.facultyContainer)
            val teacherText = requireActivity().findViewById<TextView>(R.id.teacherContainer)

            if (type == "Teacher") teacherText.text = adapter.data[position].toString()
            else facultyText.text = adapter.data[position].toString()

            dialog?.dismiss()
        }





        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dia = super.onCreateDialog(savedInstanceState)
        dia.window?.attributes?.windowAnimations = R.style.dialog_anim
        return dia

    }

}
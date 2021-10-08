package com.example.prepodsearch.choiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.prepodsearch.databinding.ChoiceFragmentBinding
import com.example.prepodsearch.listDialogFragment.ListDialogFragment
import com.example.prepodsearch.roomDataBase.teacherDataBase.TeacherDataBase

class ChoiceFragment : Fragment() {

    private lateinit var binding: ChoiceFragmentBinding
    private lateinit var viewModel: ChoiceViewModel
    private lateinit var viewModelFactory: ChoiceViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ChoiceFragmentBinding.inflate(inflater)

        val teacherDataBase = TeacherDataBase.getInstance(requireActivity().applicationContext)
        val teacherDataSource = teacherDataBase.teacherDataBaseDao
        val application = requireNotNull(activity).application
        viewModelFactory = ChoiceViewModelFactory(teacherDataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChoiceViewModel::class.java)


        binding.apply {

            searchButton.setOnClickListener {
                val teacherNameArg = binding.teacherContainer.text.toString()
                findNavController().navigate(
                    ChoiceFragmentDirections.fromChoiceToSearchResult(
                        teacherNameArg
                    )
                )

            }

            val teacherData = viewModel.getFacultyTeachers(facultyContainer.text.toString())
            teacherData.observeForever {
                if (it.isNotEmpty()) {
                    teacherContainer.text = it[0].teacherName
                }
            }


            teacherContainer.setOnClickListener {
                val dialog = ListDialogFragment("Teacher", facultyContainer.text.toString(), null)
                dialog.show(requireActivity().supportFragmentManager, "teacherChoice")
            }

            facultyContainer.setOnClickListener {
                val dialog = ListDialogFragment("Faculty", null, null)
                dialog.show(requireActivity().supportFragmentManager, "facultyChoice")

            }

        }

        return binding.root

    }


}
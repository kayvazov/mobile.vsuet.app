package com.example.vsuet.startMenuFragment.teachersMenuFragment.choiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vsuet.databinding.ChoiceFragmentBinding
import com.example.vsuet.listDialogFragment.ListDialogFragment
import com.example.vsuet.roomDataBase.teacherDataBase.TeacherDataBase
import com.example.vsuet.startMenuFragment.teachersMenuFragment.choiceFragment.ChoiceViewModel
import com.example.vsuet.startMenuFragment.teachersMenuFragment.choiceFragment.ChoiceViewModelFactory
import java.util.*

class ChoiceFragment : Fragment() {

    private lateinit var binding: ChoiceFragmentBinding
    private lateinit var viewModel: ChoiceViewModel
    private lateinit var viewModelFactory: ChoiceViewModelFactory
    private lateinit var teacherNames: MutableList<String>

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
            val numerator = if (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1 % 2 == 1) {
                0
            } else {
                1
            }
            searchButton.setOnClickListener {
                val teacherNameArg = binding.teacherContainer.text.toString()
                var names = ""
                for (name in teacherNames) {
                    names += "$name,"
                }
                findNavController().navigate(
                    ChoiceFragmentDirections.fromChoiceToSearchResult(
                        teacherNameArg,
                        names
                    )
                )
            }
            val teacherData = viewModel.getTeachers()
            teacherData.observeForever { list ->
                if (list.isNotEmpty()) {
                    teacherContainer.text = list[0].teacherName
                    teacherNames = list.map { it.teacherName }.toSet().toMutableList()
                }
            }
            teacherContainer.setOnClickListener {
                val dialog =
                    ListDialogFragment(
                        "Teacher",
                        numerator,
                        teacherNames,
                        false
                    )
                dialog.show(requireActivity().supportFragmentManager, "teacherChoice")
            }
        }
        return binding.root
    }
}
package com.example.vsuet.startMenuFragment.teachersMenuFragment.choiceFragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vsuet.databinding.ChoiceFragmentBinding
import com.example.vsuet.roomDataBase.teacherDataBase.TeacherDataBase

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
            teacherSearchButton.setOnClickListener {
                var names = ""
                for (name in teacherNames) {
                    names += "$name,"
                }
                findNavController().navigate(
                    ChoiceFragmentDirections.fromChoiceToSearchResult(
                        names
                    )
                )
            }

            audienceSearchButton.setOnClickListener {
                try {
                    startActivity(Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.NekoSoftworks.VguitMap")
                    ))
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=com.NekoSoftworks.VguitMap&hl=ru&gl=US")
                        )
                    )
                }

            }


            val teacherData = viewModel.getTeachers()
            teacherData.observeForever { list ->
                if (list.isNotEmpty()) {
                    teacherNames = list.map { it.teacherName }.toSet().toMutableList()
                }
            }
        }
        return binding.root
    }
}
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
import com.example.vsuet.roomDataBase.repository.RepositoryDataBase
import com.example.vsuet.roomDataBase.teacherDataBase.TeacherDataBase

class ChoiceFragment : Fragment() {

    private lateinit var binding: ChoiceFragmentBinding
    private lateinit var viewModel: ChoiceViewModel
    private lateinit var viewModelFactory: ChoiceViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = ChoiceFragmentBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val repository = RepositoryDataBase.getInstance(application)
        val repositoryDataSource = repository.repositoryDao
        viewModelFactory = ChoiceViewModelFactory(repositoryDataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChoiceViewModel::class.java)
        binding.apply {
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

            viewModel.getTeachers()
            viewModel.teachers.observe(viewLifecycleOwner){ list ->
                teacherSearchButton.setOnClickListener {
                    var names = ""
                    for (name in list.data) {
                        names += "$name,"
                    }
                    findNavController().navigate(
                        ChoiceFragmentDirections.fromChoiceToSearchResult(
                            names
                        )
                    )
                }
            }
        }
        return binding.root
    }
}
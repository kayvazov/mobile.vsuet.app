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

        binding.ratingSearchButton.setOnClickListener {
            findNavController().navigate(ChoiceFragmentDirections.toSearchRating(true))
        }

        binding.apply {
            audienceSearchButton.setOnClickListener {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=com.NekoSoftworks.VguitMap")
                        )
                    )
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
            viewModel.teachers.observe(viewLifecycleOwner) { list ->
                teacherSearchButton.setOnClickListener {
                    if (list != null) {
                        var names = ""
                        for (name in list.data) {
                            names += when {
                                list.data.indexOf(name) == 0 -> {
                                    "${name.substring(1)},"
                                }
                                list.data.indexOf(name) == list.data.lastIndex -> {
                                    name.substring(0, name.length - 2)
                                }
                                else -> {
                                    "$name,"
                                }
                            }
                        }
                        println(names)
                        findNavController().navigate(
                            ChoiceFragmentDirections.fromChoiceToSearchResult(
                                names
                            )
                        )
                    }
                }
            }
        }
        return binding.root
    }
}
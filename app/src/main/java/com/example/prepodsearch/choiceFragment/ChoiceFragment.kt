package com.example.prepodsearch.choiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prepodsearch.databinding.ChoiceFragmentBinding
import com.example.prepodsearch.listDialogFragment.ListDialogFragment

class ChoiceFragment : Fragment() {

    private lateinit var binding: ChoiceFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ChoiceFragmentBinding.inflate(inflater)


        binding.apply {

            searchButton.setOnClickListener {
                val teacherNameArg = binding.teacherContainer.text.toString()
                findNavController().navigate(
                    ChoiceFragmentDirections.fromChoiceToSearchResult(
                        teacherNameArg
                    )
                )

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
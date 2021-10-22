package com.example.prepodsearch.startMenuFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prepodsearch.R
import com.example.prepodsearch.databinding.FragmentStartMenuBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class StartMenuFragment : Fragment() {

    private lateinit var binding: FragmentStartMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartMenuBinding.inflate(layoutInflater)
        requireActivity().findViewById<FloatingActionButton>(R.id.goHomeButton).apply {
            animate().alpha(0f).duration = 500
            isClickable = false
        }
        binding.apply {
            teachersMenuButton.setOnClickListener {
                findNavController().navigate(StartMenuFragmentDirections.fromMenuToTeachers())
            }
            return root
        }

    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<FloatingActionButton>(R.id.goHomeButton).apply {
            alpha = 0f
            isClickable = true
            animate().alpha(1f).duration = 500
        }
    }


}
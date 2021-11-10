package com.example.vsuet.startMenuFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vsuet.R
import com.example.vsuet.databinding.FragmentStartMenuBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.internal.ApiCommand
import com.vk.api.sdk.utils.VKUtils


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
        VK
        binding.apply {
            teachersMenuButton.setOnClickListener {
                findNavController().navigate(StartMenuFragmentDirections.fromMenuToTeachers())
            }
            scheduleMenuButton.setOnClickListener {
                findNavController().navigate(StartMenuFragmentDirections.fromMenuToSchedule())
            }
            newsMenuButton.setOnClickListener {
                findNavController().navigate(StartMenuFragmentDirections.fromMenuToNews())
            }
            ratingMenuButton.setOnClickListener {
                findNavController().navigate(StartMenuFragmentDirections.fromMenuToRating())
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
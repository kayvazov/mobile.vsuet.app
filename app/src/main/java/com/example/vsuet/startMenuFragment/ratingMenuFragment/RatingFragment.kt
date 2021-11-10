package com.example.vsuet.startMenuFragment.ratingMenuFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vsuet.databinding.RatingFragmentBinding

class RatingFragment : Fragment() {


    private lateinit var viewModel: RatingViewModel
    private lateinit var binding: RatingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RatingFragmentBinding.inflate(inflater)
        return binding.root
    }


}
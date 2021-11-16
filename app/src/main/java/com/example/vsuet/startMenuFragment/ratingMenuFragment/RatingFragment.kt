package com.example.vsuet.startMenuFragment.ratingMenuFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsuet.databinding.RatingFragmentBinding
import com.example.vsuet.ratingRecyclerView.RatingRecyclerViewAdapter

class RatingFragment : Fragment() {


    private lateinit var viewModel: RatingViewModel
    private lateinit var binding: RatingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RatingFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(RatingViewModel::class.java)
        viewModel.getRating("207201")
        viewModel._rating.observeForever { list ->
            println("?")
            val adapter = RatingRecyclerViewAdapter(requireContext(), requireActivity().supportFragmentManager)
            adapter.data = list
            binding.ratingRecycler.adapter = adapter
            binding.ratingRecycler.layoutManager = LinearLayoutManager(this.context)
        }

        return binding.root
    }


}
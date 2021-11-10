package com.example.vsuet.startMenuFragment.newsMenuFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsuet.databinding.NewsFragmentBinding
import com.example.vsuet.newsRecyclerView.NewsRecyclerViewAdapter

class NewsFragment : Fragment() {


    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: NewsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.getVsuetNews()
        val newsAdapter = NewsRecyclerViewAdapter(findNavController())
        binding.newsList.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.itemsList.observeForever{ list ->
            newsAdapter.data = list
            binding.newsList.visibility = View.VISIBLE
            binding.newsLoading.visibility = View.GONE
        }
        return binding.root
    }


}
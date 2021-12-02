package com.example.vsuet.startMenuFragment.newsMenuFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsuet.databinding.NewsFragmentBinding
import com.example.vsuet.newsRecyclerView.NewsRecyclerViewAdapter
import com.example.vsuet.roomDataBase.repository.RepositoryDataBase

class NewsFragment : Fragment() {


    private lateinit var viewModel: NewsViewModel
    private lateinit var viewModelFactory: NewsViewModelFactory
    private lateinit var binding: NewsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsFragmentBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val repository = RepositoryDataBase.getInstance(application)
        val repositoryDataSource = repository.repositoryDao
        viewModelFactory = NewsViewModelFactory(application, repositoryDataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
        viewModel.getVsuetNews()
        val newsAdapter = NewsRecyclerViewAdapter()
        binding.newsList.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.getVkontakteNews()
        viewModel.response.observeForever{ list ->
            newsAdapter.data = list
            binding.newsList.visibility = View.VISIBLE
            binding.newsLoading.visibility = View.GONE
        }

        return binding.root
    }


}
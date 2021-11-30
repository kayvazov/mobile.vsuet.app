package com.example.vsuet.startMenuFragment.ratingMenuFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsuet.databinding.RatingFragmentBinding
import com.example.vsuet.ratingRecyclerView.RatingRecyclerViewAdapter
import com.example.vsuet.roomDataBase.repository.RepositoryDataBase

class RatingFragment : Fragment() {


    private lateinit var viewModel: RatingViewModel
    private lateinit var viewModelFactory: RatingViewModelFactory
    private lateinit var binding: RatingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RatingFragmentBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val repository = RepositoryDataBase.getInstance(application)
        val repositoryDataSource = repository.repositoryDao

        viewModelFactory = RatingViewModelFactory(application, repositoryDataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RatingViewModel::class.java)

        val personalAccountSettings =
            requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
        val scorer = personalAccountSettings.getString("personalScorerNumber", "")
        if(scorer == ""){
            binding.ratingRecycler.visibility = View.GONE
            binding.ratingProgressBar.visibility = View.GONE
            binding.noScorerText.visibility = View.VISIBLE
        } else {
            viewModel.getRating(scorer!!)
        }
        viewModel.rating.observeForever { list ->
            println(list)
            binding.ratingProgressBar.visibility = View.GONE
            if(list == null){
                binding.ratingRecycler.visibility = View.GONE
                binding.noScorerText.visibility = View.VISIBLE
                binding.noScorerText.text = "Неправильно введён номер зачётки"
            } else {
                val adapter = RatingRecyclerViewAdapter(
                    requireContext(),
                    requireActivity().supportFragmentManager
                )
                adapter.data = list
                binding.ratingRecycler.adapter = adapter
                binding.ratingRecycler.layoutManager = LinearLayoutManager(this.context)
            }
        }

        return binding.root
    }


}
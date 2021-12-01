package com.example.vsuet.startMenuFragment.ratingMenuFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsuet.databinding.RatingFragmentBinding
import com.example.vsuet.ratingRecyclerView.RatingRecyclerViewAdapter
import com.example.vsuet.roomDataBase.repository.RepositoryDataBase

class RatingFragment : Fragment() {


    private lateinit var viewModel: RatingViewModel
    private lateinit var viewModelFactory: RatingViewModelFactory
    private lateinit var binding: RatingFragmentBinding

    private val args: RatingFragmentArgs by navArgs()

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

        if (!args.toSearch) {
            val personalAccountSettings =
                requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
            val scorer = personalAccountSettings.getString("personalScorerNumber", "")
            if (scorer == "") {
                binding.ratingRecycler.visibility = View.GONE
                binding.ratingProgressBar.visibility = View.GONE
                binding.noScorerText.visibility = View.VISIBLE
            } else {
                viewModel.getRating(scorer!!)
            }

            val ratingParams = binding.ratingSearchContainer.layoutParams as ConstraintLayout.LayoutParams
            val averageRatingParams = binding.averageRatingText.layoutParams as ConstraintLayout.LayoutParams
            ratingParams.setMargins(0, 0, 0, 0)
            averageRatingParams.setMargins(0, 0, 0, 0)
        } else {
            binding.ratingProgressBar.visibility = View.GONE
            binding.ratingSearchContainer.visibility = View.VISIBLE
            binding.averageRatingText.visibility = View.VISIBLE
            binding.ratingSearchContainer.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE && binding.ratingSearchContainer.text.toString().length == 6) {
                    viewModel.getRating(binding.ratingSearchContainer.text.toString())
                    binding.ratingProgressBar.visibility = View.VISIBLE
                    return@setOnEditorActionListener true
                }
                false
            }
        }
        viewModel.rating.observeForever { list ->
            if (list == null) {
                binding.ratingRecycler.visibility = View.GONE
                binding.noScorerText.visibility = View.VISIBLE
                binding.noScorerText.text = "Неправильно введён номер зачётки"
            } else {
                val adapter = RatingRecyclerViewAdapter(
                    requireContext(),
                    requireActivity().supportFragmentManager
                )

                binding.ratingProgressBar.visibility = View.GONE

                adapter.data = list
                binding.ratingRecycler.adapter = adapter
                binding.ratingRecycler.layoutManager = LinearLayoutManager(this.context)
                if(list.isNotEmpty())
                if (args.toSearch) {
                    var sumOfRating = 0
                    for (rating in list) {
                        sumOfRating += rating.value[26].toInt()
                    }
                    binding.averageRatingText.text =
                        binding.averageRatingText.text.toString() + (sumOfRating.toFloat() / list.size).toString()
                }
            }
        }

        return binding.root
    }


}
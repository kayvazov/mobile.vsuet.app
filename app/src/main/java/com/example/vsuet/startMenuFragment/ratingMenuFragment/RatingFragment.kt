package com.example.vsuet.startMenuFragment.ratingMenuFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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

        binding.apply {
            if (!args.toSearch) {
                val personalAccountSettings =
                    requireActivity().getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
                val scorer = personalAccountSettings.getString("personalScorerNumber", "")
                if (scorer == "") {
                    ratingRecycler.visibility = View.GONE
                    ratingProgressBar.visibility = View.GONE
                    noScorerText.visibility = View.VISIBLE
                } else {
                    viewModel.getRating(scorer!!)
                }
                val ratingParams =
                    ratingSearchContainer.layoutParams as ConstraintLayout.LayoutParams
                val averageRatingParams =
                    averageRatingText.layoutParams as ConstraintLayout.LayoutParams
                ratingParams.setMargins(0, 0, 0, 0)
                averageRatingParams.setMargins(0, 0, 0, 0)
            } else {
                ratingProgressBar.visibility = View.GONE
                ratingSearchContainer.visibility = View.VISIBLE
                averageRatingText.visibility = View.VISIBLE
                ratingSearchContainer.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE && ratingSearchContainer.text.toString().length == 6) {
                        viewModel.getRating(ratingSearchContainer.text.toString())
                        ratingProgressBar.visibility = View.VISIBLE
                        return@setOnEditorActionListener true
                    }
                    false
                }
            }
            viewModel.rating.observeForever { list ->
                if (list == null) {
                    ratingRecycler.visibility = View.GONE
                    noScorerText.visibility = View.VISIBLE
                    noScorerText.text = "Неправильно введён номер зачётки"
                } else {
                    val adapter = RatingRecyclerViewAdapter(
                        requireContext(),
                        requireActivity().supportFragmentManager
                    )

                    ratingProgressBar.visibility = View.GONE

                    adapter.data = list
                    ratingRecycler.adapter = adapter
                    ratingRecycler.layoutManager = LinearLayoutManager(this@RatingFragment.context)
                    if (list.isNotEmpty())
                        if (args.toSearch) {
                            var sumOfRating = 0
                            for (rating in list) {
                                sumOfRating += rating.value[26].toInt()
                            }
                            averageRatingText.text =
                                "Средний рейтинг" + (sumOfRating.toFloat() / list.size).toString()
                        }
                }
            }

            return root
        }
    }


}
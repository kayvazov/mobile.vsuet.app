package com.example.vsuet.startMenuFragment.newsMenuFragment.newsInsideFragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.vsuet.R
import com.example.vsuet.databinding.FragmentNewsInsideBinding

class NewsInsideFragment : Fragment() {



    private lateinit var binding: FragmentNewsInsideBinding
    private lateinit var viewModel: NewsInsideViewModel
    private lateinit var viewModelFactory: NewsInsideViewModelFactory

    private val args by navArgs<NewsInsideFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsInsideBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        viewModelFactory = NewsInsideViewModelFactory(args.itemInsideLink, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsInsideViewModel::class.java)

        binding.newsInsideTitle.text = args.itemTitle

        viewModel.newsItemText.observeForever { text ->
            binding.newsInsideText.text = text
            binding.newsInsideText.movementMethod = ScrollingMovementMethod()
        }


        viewModel.setItemThings()


        return binding.root
    }
}
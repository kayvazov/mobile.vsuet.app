package com.example.vsuet.deaneryFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vsuet.R

class DeaneryFragment : Fragment() {

    companion object {
        fun newInstance() = DeaneryFragment()
    }

    private lateinit var viewModel: DeaneryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.deanery_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DeaneryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
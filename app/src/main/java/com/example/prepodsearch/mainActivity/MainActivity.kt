package com.example.prepodsearch.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.prepodsearch.MainNavDirections
import com.example.prepodsearch.R
import com.example.prepodsearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            goHomeButton.alpha = 0f
            supportActionBar?.title = ""
            goHomeButton.setOnClickListener {
                findNavController(R.id.nav_host_fragment).navigate(MainNavDirections.goHomeAction())
                it.isClickable = false
            }
        }
    }
}
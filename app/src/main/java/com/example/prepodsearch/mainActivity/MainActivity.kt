package com.example.prepodsearch.mainActivity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.prepodsearch.MainNavDirections
import com.example.prepodsearch.R
import com.example.prepodsearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.goHomeButton.alpha = 0f
        val bindingView = binding.root
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        supportActionBar?.title = ""
        setContentView(bindingView)

        binding.goHomeButton.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(MainNavDirections.goHomeAction())
            it.isClickable = false
        }

    }


}
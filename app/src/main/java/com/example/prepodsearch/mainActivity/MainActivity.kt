package com.example.prepodsearch.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prepodsearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val bindingView = binding.root
        setContentView(bindingView)






    }


}
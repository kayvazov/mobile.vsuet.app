package com.example.prepodsearch.splashScreenActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.prepodsearch.databinding.ActivitySplashBinding
import com.example.prepodsearch.mainActivity.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            splashImage.alpha = 0f
            splashImage.animate().alpha(1f).duration = 1500
            Handler(Looper.myLooper()!!).postDelayed({
                splashImage.animate().alpha(0f).duration = 1500
            }, 1500)
        }
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }, 3000)
    }
}
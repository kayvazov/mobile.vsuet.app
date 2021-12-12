package com.example.vsuet.splashScreenActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.vsuet.NonStopNotificationBroadcast.NonstopServiceBroadcast
import com.example.vsuet.R
import com.example.vsuet.databinding.ActivitySplashBinding
import com.example.vsuet.mainActivity.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val broadcast = "com.example.vsuet.NonStopNotificationBroadcast"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            splashImage.alpha = 0f
            Handler(Looper.myLooper()!!).postDelayed({
                splashImage.animate().alpha(1f).duration = 1500
            }, 500)
            Handler(Looper.myLooper()!!).postDelayed({
                splashImage.animate().alpha(0f).duration = 1500
            }, 2300)
        }
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.splash_enter_animation, R.anim.splash_exit_animation)
        }, 4000)
    }
}
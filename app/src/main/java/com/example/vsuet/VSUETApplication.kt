package com.example.vsuet

import android.app.Application
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class VSUETApplication : Application() {

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            println("whatever")
        }
    }

    override fun onCreate() {
        super.onCreate()

        VK.initialize(this)
        VK.addTokenExpiredHandler(tokenTracker)

    }
}
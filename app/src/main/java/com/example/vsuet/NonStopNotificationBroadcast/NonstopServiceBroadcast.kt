package com.example.vsuet.NonStopNotificationBroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.vsuet.NotificationService.NotificationService

class NonstopServiceBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val isPushOn = context.getSharedPreferences("accountSettings", Context.MODE_PRIVATE).getBoolean("isPushOn", false)
        if (intent.action == Intent.ACTION_BOOT_COMPLETED && isPushOn) {
            Intent(context, NotificationService::class.java).also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(it)
                    return
                }
                context.startService(it)
            }
        }
    }
}
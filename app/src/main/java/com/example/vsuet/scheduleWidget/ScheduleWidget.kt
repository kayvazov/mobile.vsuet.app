package com.example.vsuet.scheduleWidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class ScheduleWidget : AppWidgetProvider() {

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        println("deleted")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        println("disabled")
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)

        println("enabled")
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

}
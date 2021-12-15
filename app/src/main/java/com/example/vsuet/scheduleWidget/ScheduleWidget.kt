package com.example.vsuet.scheduleWidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.example.vsuet.R

class ScheduleWidget : AppWidgetProvider() {

    private lateinit var updateRunnable: Runnable
    private lateinit var updateHandler: Handler

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        println("deleted")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        if (appWidgetIds != null) {
            for (i in appWidgetIds) {
                println(i)
                updateWidget(context, appWidgetManager, i)
            }
        }

        updateHandler = Handler(Looper.myLooper()!!)
        updateRunnable = Runnable {
            println("working 2")
            val appWidgetId = appWidgetManager?.getAppWidgetIds(ComponentName(context!!, ScheduleWidget::class.java))
            println(appWidgetId)
            appWidgetManager?.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetLessonList)
            if (appWidgetIds != null) {
                for (i in appWidgetIds) {
                    updateWidget(context, appWidgetManager, i)
                }
            }
            updateLoop()
        }


        updateHandler.postDelayed(updateRunnable, 10000)

    }

    fun updateLoop(){
        println("working 1")
        updateHandler.postDelayed(updateRunnable, 10000)
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    private fun updateWidget(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context?.packageName, R.layout.schedule_widget_layout)
        setList(views, context, appWidgetId)
        appWidgetManager?.updateAppWidget(appWidgetId, views)
    }

    private fun updateRemoteViews(rv: RemoteViews, context: Context?, appWidgetId: Int) {
        val updateIntent = Intent(context, WidgetService::class.java)
        updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, arrayOf(appWidgetId))
    }

    private fun setList(rv: RemoteViews, context: Context?, appWidgetId: Int) {
        val adapter = Intent(context, WidgetService::class.java)
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        rv.setRemoteAdapter(R.id.widgetLessonList, adapter)
    }

}
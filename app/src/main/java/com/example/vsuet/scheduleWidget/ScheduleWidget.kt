package com.example.vsuet.scheduleWidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.example.vsuet.R
import okhttp3.internal.wait

class ScheduleWidget : AppWidgetProvider() {

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
                updateWidget(context,appWidgetManager,i)
            }
            println("something happens")
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    private fun updateWidget(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int) {
        val views = RemoteViews(context?.packageName, R.layout.schedule_widget_layout)
        setList(views, context, appWidgetId)
        appWidgetManager?.updateAppWidget(appWidgetId, views)
    }

    private fun updateRemoteViews(rv: RemoteViews, context: Context?, appWidgetId: Int){
        val updateIntent = Intent(context, WidgetService::class.java)
        updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, arrayOf(appWidgetId))
    }

    private fun setList(rv: RemoteViews, context: Context?, appWidgetId: Int){
        val adapter = Intent(context, WidgetService::class.java)
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        rv.setRemoteAdapter(R.id.widgetLessonList, adapter)
    }

}
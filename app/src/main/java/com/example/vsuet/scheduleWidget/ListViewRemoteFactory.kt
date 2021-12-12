package com.example.vsuet.scheduleWidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.lifecycle.MutableLiveData
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.R
import com.example.vsuet.roomDataBase.repository.RepositoryDataBase
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class ListViewRemoteFactory(val context: Context, val intent: Intent) :
    RemoteViewsService.RemoteViewsFactory {

    val widgetId = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
    )

    private lateinit var data: List<LessonProperty>
    private var schedule = MutableLiveData<List<LessonProperty>>()
    private val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        Calendar.TUESDAY -> "вторник"
        Calendar.WEDNESDAY -> "среда"
        Calendar.THURSDAY -> "четверг"
        Calendar.FRIDAY -> "пятница"
        Calendar.SATURDAY -> "суббота"
        Calendar.SUNDAY -> "воскресенье"
        else -> "понедельник"
    }

    private suspend fun getSchedule() {
        val repoDataBase = RepositoryDataBase.getInstance(context)
        coroutineScope {
            withContext(Dispatchers.IO) {
                schedule.postValue(repoDataBase.repositoryDao.getSchedule())
                println(schedule.value)
            }
        }
    }

    @DelicateCoroutinesApi
    fun getRealSchedule() {
        GlobalScope.launch {
            getSchedule()
        }
    }

    override fun getCount(): Int {
        while (data.isEmpty()) {

        }
        return data.size
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }


    override fun onCreate() {
        val views = RemoteViews(context.packageName, R.layout.schedule_widget_layout)
        data = listOf()
        getRealSchedule()
        val account = context.getSharedPreferences("accountSettings", Context.MODE_PRIVATE)
        val group = account.getString("groupNumber", "0")
        val subgroup = account.getString("underGroupNumber", "1")
        schedule.observeForever { list ->
            println(list)
            val currentNumerator = (Calendar.getInstance()
                .get(Calendar.WEEK_OF_YEAR) % 2 == 0)
            data = list.filter {
                it.day == dayOfWeek && it.weekType == currentNumerator && it.group == group && it.subgroup == subgroup!!.toInt()
            }
            views.setViewVisibility(R.id.widgetNoLessons, View.GONE)
            if (data.isEmpty()) {
                var nextDay = ""
                val days = listOf(
                    "понедельник",
                    "вторник",
                    "среда",
                    "четверг",
                    "пятница",
                    "суббота",
                    "воскресенье"
                )
                for (i in days.indexOf(dayOfWeek) + 1 until days.size) {
                    if (data.any { it.day == days[i] && it.weekType == currentNumerator && it.subgroup == subgroup!!.toInt() && group == it.group }) {
                        nextDay = days[i]
                        break
                    }
                }
                if (nextDay == "") {
                    for (i in days) {
                        if (list.any { it.day == i && it.weekType == !currentNumerator && it.subgroup == subgroup!!.toInt() && group == it.group }) {
                            nextDay = i
                            val dayList = listOf(
                                "понедельник",
                                "вторник",
                                "среду",
                                "четверг",
                                "пятницу",
                                "субботу",
                                "воскресенье"
                            )
                            val fineNextDayText = dayList[days.indexOf(nextDay)]
                            println("we got here")
                            views.setViewVisibility(R.id.widgetNoLessons, View.VISIBLE)
                            views.setTextViewText(
                                R.id.widgetNoLessons,
                                "На сегодня всё, пары на $fineNextDayText:"
                            )
                            data = if (nextDay == "суббота" || nextDay == "восресенье") {
                                list.filter { it.day == nextDay && it.subgroup == subgroup!!.toInt() && it.weekType == !currentNumerator && group == it.group }
                            } else {
                                list.filter { it.day == nextDay && it.subgroup == subgroup!!.toInt() && it.weekType == !currentNumerator && group == it.group }
                            }
                            break
                        }
                    }
                } else {
                    data =
                        list.filter { it.day == nextDay && it.subgroup == subgroup!!.toInt() && it.weekType == currentNumerator && group == it.group }
                }

                val appWidgetManager = AppWidgetManager.getInstance(context)
                appWidgetManager.updateAppWidget(widgetId, views)
                appWidgetManager.updateAppWidget(
                    widgetId,
                    RemoteViews(context.packageName, R.layout.widget_list_view_item)
                )

            }
        }
    }

    override fun getViewAt(p0: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_list_view_item)
        val item = data[p0]
        remoteViews.setTextViewText(R.id.widgetTeacher, item.teacher)
        remoteViews.setTextViewText(R.id.widgetLesson, item.name)
        remoteViews.setTextViewText(R.id.widgetTime, item.time.start + "-" + item.time.end)
        println("????")
        val date = Date()
        val formatterHours = SimpleDateFormat("HH")
        val formatterMinutes = SimpleDateFormat("mm")
        val hours = formatterHours.format(date).toInt()
        val minutes = formatterMinutes.format(date).toInt()
        val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> "вторник"
            Calendar.WEDNESDAY -> "среда"
            Calendar.THURSDAY -> "четверг"
            Calendar.FRIDAY -> "пятница"
            Calendar.SATURDAY -> "суббота"
            Calendar.SUNDAY -> "воскресенье"
            else -> "понедельник"
        }

        val startTimeHours = item.time.start.split(".")[0].toInt()
        val endTimeHours = item.time.end.split(".")[0].toInt()
        val startTimeMinutes = item.time.start.split(".")[1].toInt()
        val endTimeMinutes = item.time.end.split(".")[1].toInt()
        val appWidgetManager = AppWidgetManager.getInstance(context)
        println(startTimeHours)
        println(hours)
        println(endTimeHours)
        if (((startTimeHours < hours && endTimeHours >= hours && endTimeMinutes < minutes) || (startTimeHours < hours && endTimeHours > hours) || ((startTimeHours == hours && startTimeMinutes > minutes) || (endTimeHours == hours && endTimeMinutes < minutes))) && item.day == dayOfWeek) {
            println("CURRENT")
            remoteViews.setTextViewText(R.id.lessonIndicator, "Занятие идёт")
            remoteViews.setTextColor(R.id.lessonIndicator, Color.parseColor("#FF0000"))
            item.time.isCurrent = true
        } else if (data.indexOf(item) != 0) {
            if (data[p0 - 1].time.isCurrent) {
                item.time.isCurrent = false
                remoteViews.setTextViewText(R.id.lessonIndicator, "Следующая пара")
            } else {
                item.time.isCurrent = false
                remoteViews.setViewVisibility(R.id.lessonIndicator, View.GONE)
            }
        } else {
            item.time.isCurrent = false
            remoteViews.setViewVisibility(R.id.lessonIndicator, View.GONE)
        }

        return remoteViews
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDataSetChanged() {

    }

    override fun onDestroy() {

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun hasStableIds(): Boolean {
        return true
    }


}
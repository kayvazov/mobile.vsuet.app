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

    private val breakList = mutableListOf<LessonProperty>()

    private var nextPairTime = "0"
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
            }
        }
    }

    private fun timeComparator(firstTime: List<String>, secondTime: List<String>): Boolean {

        return if (firstTime[0] != "" && secondTime[0] != "" && firstTime[0] != secondTime[0]) {
            firstTime[0].toInt() > secondTime[0].toInt()
        } else {
            if (firstTime[1] != "" && secondTime[1] != "") {
                firstTime[1].toInt() >= secondTime[1].toInt()
            } else {
                if (firstTime[1] == "" && secondTime[1] == "") {
                    true
                } else firstTime[1] != ""
            }
        }


    }

    override fun getViewAt(p0: Int): RemoteViews {
        println("GOT IT")
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_list_view_item)
        val item = data[p0]
        remoteViews.setTextViewText(R.id.widgetTeacher, item.teacher)
        remoteViews.setTextViewText(R.id.widgetLesson, item.name)
        remoteViews.setTextViewText(R.id.widgetTime, item.time.start + "-" + item.time.end)
        val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> "вторник"
            Calendar.WEDNESDAY -> "среда"
            Calendar.THURSDAY -> "четверг"
            Calendar.FRIDAY -> "пятница"
            Calendar.SATURDAY -> "суббота"
            Calendar.SUNDAY -> "воскресенье"
            else -> "понедельник"
        }

        val date = Date()
        val formatterHours = SimpleDateFormat("HH")
        val formatterMinutes = SimpleDateFormat("mm")
        val hours = formatterHours.format(date)
        val minutes = formatterMinutes.format(date)
        val hoursMinutes = listOf(hours, minutes)

        val pairTimeVaries = listOf(
            "8.00-9.45",
            "9.45-11.20",
            "11.50-13.25",
            "13.35-15.10",
            "15.20-16.55",
            "17.05-18.40",
            "18.50-20.25"
        )
        val pairTime = if (timeComparator(hoursMinutes, "8:00".split(":")) && timeComparator(
                "9:35".split(":"),
                hoursMinutes
            )
        ) {
            pairTimeVaries[0]
        } else if (timeComparator(
                hoursMinutes,
                "9:45".split(":")
            ) && timeComparator("11:20".split(":"), hoursMinutes)
        ) {
            pairTimeVaries[1]
        } else if (timeComparator(hoursMinutes, "11:50".split(":")) && timeComparator(
                "13:25".split(
                    ":"
                ), hoursMinutes
            )
        ) {
            pairTimeVaries[2]
        } else if (timeComparator(hoursMinutes, "13:35".split(":")) && timeComparator(
                "15:10".split(
                    ":"
                ), hoursMinutes
            )
        ) {
            pairTimeVaries[3]
        } else if (timeComparator(hoursMinutes, "15:20".split(":")) && timeComparator(
                "16:55".split(
                    ":"
                ), hoursMinutes
            )
        ) {
            pairTimeVaries[4]
        } else if (timeComparator(hoursMinutes, "17:05".split(":")) && timeComparator(
                "18:40".split(
                    ":"
                ), hoursMinutes
            )
        ) {
            pairTimeVaries[5]
        } else if (timeComparator(hoursMinutes, "18:50".split(":")) && timeComparator(
                "20:25".split(
                    ":"
                ), hoursMinutes
            )
        ) {
            pairTimeVaries[6]
        } else "Перерыв"


        val currentLessonTime = item.time.start + "-" + item.time.end
        if (pairTime == "Перерыв") {
            for (i in data) {
                if (timeComparator(i.time.start.split("."), hoursMinutes)) {
                    breakList.add(i)
                }
            }
        }
        println("NextPair")
        println(nextPairTime)
        if (currentLessonTime == pairTime && item.day == dayOfWeek) {
            remoteViews.setTextViewText(R.id.lessonIndicator, "Занятие идёт")
            remoteViews.setViewVisibility(R.id.lessonIndicator, View.VISIBLE)
            remoteViews.setTextColor(R.id.lessonIndicator, Color.parseColor("#FF0000"))
        } else if (nextPairTime == currentLessonTime) {
            remoteViews.setViewVisibility(R.id.lessonIndicator, View.VISIBLE)
            remoteViews.setTextViewText(R.id.lessonIndicator, "Следующая пара")
        } else {
            remoteViews.setViewVisibility(R.id.lessonIndicator, View.GONE)
        }

        if (data.indexOf(item) + 1 < data.size) {
            nextPairTime = when (pairTime) {
                "Перерыв" -> {
                    breakList.first().time.start + "-" + breakList.first().time.end
                }
                currentLessonTime -> {
                    data[data.indexOf(item) + 1].time.start + "-" + data[data.indexOf(
                        item
                    ) + 1].time.end
                }
                else -> {
                    "0"
                }
            }
        }

        return remoteViews
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDataSetChanged() {
        println("dataSetUpdated")
        getRealSchedule()
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
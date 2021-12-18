package com.example.vsuet.NotificationService


import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.vsuet.API.LessonProperty
import com.example.vsuet.R
import com.example.vsuet.mainActivity.MainActivity
import com.example.vsuet.roomDataBase.repository.RepositoryDataBase
import kotlinx.coroutines.*
import java.lang.Runnable
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class NotificationService() : Service() {

    private lateinit var notificationHandler: Handler
    private lateinit var notificationRunnable: Runnable
    private var schedule = MutableLiveData<List<LessonProperty>>()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()
        val notification = lessonCreateNotification(null)
        startForeground(1, notification)
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private fun lessonCreateNotification(lesson: LessonProperty?): Notification {
        val notificationChannelId = "ENDLESS SERVICE CHANNEL"
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Endless Service notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Endless Service channel"

                it.vibrationPattern = longArrayOf(100, 150, 150, 100)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder: NotificationCompat.Builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) NotificationCompat.Builder(
                this,
                notificationChannelId
            ) else NotificationCompat.Builder(this)
        var notification = builder
            .setContentTitle("Пары")
            .setContentText("Уведомления подключены")
            .setSmallIcon(R.drawable.splash_vsuet_image)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(null)
            .setPriority(Notification.PRIORITY_HIGH)
            .build()


        if (lesson != null) {

            notification = builder
                .setContentTitle(lesson.name)
                .setContentText("${lesson.audience} каб. ${lesson.time.start}")
                .setSmallIcon(R.drawable.splash_vsuet_image)
                .setSound(null)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.splash_vsuet_image))
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("${lesson.audience} каб. ${lesson.time.start}")
                )
                .build()
            notificationManager.notify(2131, notification)
        }




        return notification

    }


    private fun toDoNotificationTime(lesson: LessonProperty): String {

        val oldTime = lesson.time.start.split(".")
        val beforeTime = 5

        var oldHours = oldTime[0].toInt()
        var oldMinutes = oldTime[1].toInt()

        if (oldMinutes - beforeTime < 0) {
            oldHours--
            oldMinutes = 60 - kotlin.math.abs(oldMinutes - beforeTime)
        } else {
            oldMinutes -= beforeTime
        }


        return "$oldHours.$oldMinutes"


    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        notificationHandler = Handler(Looper.myLooper()!!)
        notificationRunnable = Runnable {
            checkToDoTime()
            println("working 1")
        }
        notificationHandler.postDelayed(notificationRunnable, 60000)

        return START_STICKY

    }

    @SuppressLint("SimpleDateFormat")
    private fun takeCurrentTime(): String {
        val c = Calendar.getInstance()

        val timeFormatter = SimpleDateFormat("HH.mm", DateFormatSymbols())


        return timeFormatter.format(c.time)

    }


    private suspend fun getSchedule() {
        val repoDataBase = RepositoryDataBase.getInstance(applicationContext)
        coroutineScope {
            withContext(Dispatchers.IO) {
                schedule = MutableLiveData()
                schedule.postValue(repoDataBase.repositoryDao.getSchedule())
            }
        }
    }

    @DelicateCoroutinesApi
    fun getRealSchedule() {
        GlobalScope.launch {
            getSchedule()
        }
    }

    @DelicateCoroutinesApi
    private fun checkToDoTime() {
        val dayOfWeek = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> "вторник"
            Calendar.WEDNESDAY -> "среда"
            Calendar.THURSDAY -> "четверг"
            Calendar.FRIDAY -> "пятница"
            Calendar.SATURDAY -> "суббота"
            Calendar.SUNDAY -> "воскресенье"
            else -> "понедельник"
        }
        println(dayOfWeek)
        getRealSchedule()


        var currentTime = takeCurrentTime()
        if (currentTime[0] == '0') {
            currentTime = currentTime.substring(1)
        }

        schedule.observeForever { list ->
            if (list.isNotEmpty()) {
                for (i in list.filter {
                    it.weekType == (Calendar.getInstance()
                        .get(Calendar.WEEK_OF_YEAR) % 2 == 0) && it.day == dayOfWeek
                }) {
                    if (currentTime == toDoNotificationTime(i)) {
                        lessonCreateNotification(i)
                    }
                }
            }
        }
        println("working 2")

        notificationHandler.postDelayed(notificationRunnable, 60000)
    }

}

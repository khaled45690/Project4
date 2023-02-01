package com.udacity.project4.Utils


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.udacity.project4.R


class Notifications(
    parsedContext: Context,
    myIntent: PendingIntent,
    showStatusPendingIntent: PendingIntent
){
    private lateinit var notificationManager: NotificationManager
    private var parsedContext: Context = parsedContext
    private val myIntent : PendingIntent = myIntent
    private val CHANNEL_ID = "channelId"
    private val showStatusPendingIntent = showStatusPendingIntent


    private val channel = NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_HIGH).apply {
        description = "descriptionText"
    }




    fun getNotification(systemService: Any , title:String ,text:String ) {
//        Context.NOTIFICATION_SERVICE
        val builder = NotificationCompat.Builder(parsedContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_location)
            .setContentTitle(title)
            .setContentText(text)

            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(myIntent)
            .setAutoCancel(true)
            .addAction(
                R.drawable.ic_location,
                "Show Reminder",
                showStatusPendingIntent
            )

        notificationManager = systemService as NotificationManager
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(0, builder.build())
    }


}
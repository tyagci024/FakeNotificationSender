package com.example.fakenotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.Date

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val appIcon = intent.getStringExtra("appIcon") ?: "insta"
        val notificationTitle = intent.getStringExtra("notificationTitle") ?: "Default title"
        val notificationText = intent.getStringExtra("notificationText") ?: "Default text"
        Log.d("AlarmReceiver", "Alarm triggered at ${Date()}, Icon: $appIcon")
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(context, notificationTitle, notificationText, appIcon)
    }


    private fun NotificationManager.sendNotification(
        context: Context,
        title: String,
        text: String,
        appIcon: String
    ) {
        val notificationChannelId = "notificationChannelId"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Alarm Notification",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alarm Notification"
            }
            this.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, notificationChannelId)
            .setSmallIcon(getIconResourceId(appIcon))
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_ALL)
            .build()

        this.notify(0, notification)
    }

    private fun getIconResourceId(iconName: String): Int {
        return when (iconName) {
            "whatsapp" -> R.drawable.whatsapp
            "twitter" -> R.drawable.twitter
            "sms" -> R.drawable.sms
            "instagram" -> R.drawable.insta

            else -> R.drawable.ic_notification
        }
    }
}

package com.example.subtracker.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.subtracker.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class RenewalAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val database = AppDatabase.getInstance(context)

        CoroutineScope(Dispatchers.IO).launch {
            val threeDaysFromNow = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3)
            val dueSoon = database.subscriptionDao().getDueBefore(threeDaysFromNow)

            if (dueSoon.isNotEmpty()) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val channelId = "renewal_alerts"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        "Subscription Renewals",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    notificationManager.createNotificationChannel(channel)
                }

                val title = if (dueSoon.size == 1) {
                    "Upcoming renewal: ${dueSoon.first().name}"
                } else {
                    "${dueSoon.size} subscriptions renewing soon"
                }

                val notification = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)
                    .setContentText("Check your upcoming bills to avoid unexpected charges.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .build()

                notificationManager.notify(101, notification)
            }
        }
    }
}

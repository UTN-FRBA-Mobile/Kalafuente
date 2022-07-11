package com.example.quecomohoy.notifications

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.quecomohoy.R
import android.os.Bundle
import androidx.annotation.IdRes
import com.example.quecomohoy.ui.searchrecipes.RecipesFragment
import java.util.*


const val LUNCH_CHANNEL_ID = "lunch"
const val LUNCH_NOTIFICATION_ID = 1
const val DINNER_CHANNEL_ID = "dinner"
const val DINNER_NOTIFICATION_ID = 2

class MealRecommendationsNotification: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val channelId = intent.getStringExtra("channelId")!!
        val notificationId = intent.getIntExtra("notificationId", -1)
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_fav)
            .setContentTitle("¿No sabés qué comer?")
            .setContentText("¡Mira estas recetas especiales para vos!")
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)
    }

    companion object {
        fun scheduleNotification(context: Context, channelId : String, notificationId: Int, timeInMillis : Long) {
            val intent = Intent(context, MealRecommendationsNotification::class.java)
            intent.putExtra("channelId", channelId)
            intent.putExtra("notificationId", notificationId)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                24 * 60 *60 * 1000,
                pendingIntent
            )
        }
    }
}
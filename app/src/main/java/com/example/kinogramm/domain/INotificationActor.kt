package com.example.kinogramm.domain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.R
import com.example.kinogramm.util.Constants
import com.example.kinogramm.view.MainActivity

private const val NOTIFICATION_ID = 101
private const val CHANNEL_ID = "kinogramm_channel"
private const val CHANNEL_DESCRIPTION = "kinogramm_notification_channel"

@ExperimentalPagingApi
interface INotificationActor {

    fun doNotify(context: Context, params: Map<String, String>) {
        createNotificationChannel(context)

        val builder =
            getNotifBuilder(context, params)

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getNotifBuilder(
        context: Context,
        params: Map<String, String>
    ): NotificationCompat.Builder {
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(
                Constants.EXTRA_FILM_REMOTE_ID,
                params[Constants.EXTRA_FILM_REMOTE_ID]
            )
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            openAppIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle("Don't forget to watch a movie!")
            .setContentText(params[Constants.EXTRA_FILM_TITLE])
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }
}
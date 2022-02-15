package com.example.kinogramm.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.util.Constants

private const val TAG = "ScheduledFilmAlarmReceiver"

@ExperimentalPagingApi
class ScheduledFilmAlarmReceiver : BroadcastReceiver(), INotificationActor {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Intent received.")
        intent ?: run {
            Log.e(TAG, "Intent is null.")
            return
        }
        context ?: run {
            Log.e(TAG, "Context is null.")
            return
        }

        with(intent) {
            val filmRemoteId = getIntExtra(Constants.EXTRA_FILM_REMOTE_ID, 0).toString()
            val filmTitle = getStringExtra(Constants.EXTRA_FILM_TITLE) ?: ""
            val params = mapOf(
                Constants.EXTRA_FILM_REMOTE_ID to filmRemoteId,
                Constants.EXTRA_FILM_TITLE to filmTitle
            )

            doNotify(context, params)
        }
    }
}
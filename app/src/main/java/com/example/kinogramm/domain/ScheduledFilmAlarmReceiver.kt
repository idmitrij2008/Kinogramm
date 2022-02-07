package com.example.kinogramm.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.kinogramm.util.Constants

private const val TAG = "ScheduledFilmAlarmReceiver"

class ScheduledFilmAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Intent received.")
        intent ?: run {
            Log.d(TAG, "Intent is null.")
            return
        }
        context ?: run {
            Log.d(TAG, "Context is null.")
            return
        }

        val filmTitle = intent.getStringExtra(Constants.EXTRA_FILM_TITLE)
    }

}
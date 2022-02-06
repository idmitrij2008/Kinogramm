package com.example.kinogramm.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

private const val TAG = "ScheduledFilmAlarmReceiver"

class ScheduledFilmAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "Received")
    }

}
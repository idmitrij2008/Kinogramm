package com.example.kinogramm.domain

import android.content.Context
import android.util.Log
import com.example.kinogramm.util.Constants
import com.google.firebase.messaging.FirebaseMessagingService

private const val TAG = "MyFirebaseMessagingService"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "Token: $token")

        getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(Constants.FIREBASE_MESSAGING_TOKEN_NAME, token).apply()
    }
}
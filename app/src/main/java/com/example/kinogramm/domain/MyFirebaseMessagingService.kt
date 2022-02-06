package com.example.kinogramm.domain

import android.util.Log
import com.example.kinogramm.di.Injection
import com.example.kinogramm.util.Constants
import com.google.firebase.messaging.FirebaseMessagingService

private const val TAG = "MyFirebaseMessagingService"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "Token: $token")

        Injection.provideSharedPreferences(application)
            .edit()
            .putString(Constants.FIREBASE_MESSAGING_TOKEN_NAME, token)
            .apply()
    }
}
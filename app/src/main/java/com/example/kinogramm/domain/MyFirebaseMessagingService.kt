package com.example.kinogramm.domain

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.util.Constants.EXTRA_FILM_REMOTE_ID
import com.example.kinogramm.util.Constants.EXTRA_FILM_TITLE
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val TAG = "MyFirebaseMessagingService"

@ExperimentalPagingApi
class MyFirebaseMessagingService : FirebaseMessagingService(), INotificationActor {

    override fun onNewToken(token: String) {
        Log.d(TAG, "Token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Remote message: $remoteMessage")
        remoteMessage.notification

        val params = mapOf(
            EXTRA_FILM_REMOTE_ID to remoteMessage.data.getOrDefault(EXTRA_FILM_REMOTE_ID, ""),
            EXTRA_FILM_TITLE to remoteMessage.data.getOrDefault(EXTRA_FILM_TITLE, "")
        )

        doNotify(this, params)
    }
}
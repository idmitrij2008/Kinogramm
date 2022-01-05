package com.example.kinogramm.data.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpClient {
    val instance: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()
}
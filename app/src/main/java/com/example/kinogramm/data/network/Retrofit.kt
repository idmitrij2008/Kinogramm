package com.example.kinogramm.data.network

import com.example.kinogramm.util.Constants.API_BASE_URL
import retrofit2.Retrofit

object Retrofit {
    val instance: Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(OkHttpClient.instance)
        .addConverterFactory(GsonConverterFactory.instance)
        .build()
}
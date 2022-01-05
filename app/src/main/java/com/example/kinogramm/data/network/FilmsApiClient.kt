package com.example.kinogramm.data.network

object FilmsApiClient {
    val instance: FilmsApi = Retrofit.instance.create(FilmsApi::class.java)
}
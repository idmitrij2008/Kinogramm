package com.example.kinogramm.data.network

import retrofit2.converter.gson.GsonConverterFactory

object GsonConverterFactory {
    val instance: GsonConverterFactory = GsonConverterFactory.create()
}
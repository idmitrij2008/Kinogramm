package com.example.kinogramm.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsApi {
    @GET("movie/popular")
    suspend fun getPopularFilms(@Query("api_key") apiKey: String): Response<PopularFilmsApiResponse>
}
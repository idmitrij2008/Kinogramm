package com.example.kinogramm.data.network

import com.example.kinogramm.util.Constants.API_KEY
import com.example.kinogramm.util.Constants.QUERY_API_KEY
import retrofit2.Response
import retrofit2.http.GET

interface FilmsApi {
    @GET("movie/popular?$QUERY_API_KEY=$API_KEY")
    suspend fun getPopularFilms(): Response<PopularFilmsApiResponse>
}
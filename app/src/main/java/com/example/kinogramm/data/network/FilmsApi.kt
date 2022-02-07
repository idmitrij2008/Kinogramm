package com.example.kinogramm.data.network

import com.example.kinogramm.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsApi {
    @GET("movie/popular")
    suspend fun getPopularFilms(
        @Query(Constants.QUERY_API_KEY) apiKey: String,
        @Query(Constants.QUERY_PAGE) page: Int
    ): Response<PopularFilmsApiResponse>
}
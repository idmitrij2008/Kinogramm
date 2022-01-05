package com.example.kinogramm.data.network


import com.google.gson.annotations.SerializedName

data class PopularFilmsApiResponse(
    @SerializedName("results")
    val films: List<FilmApiModel>
)
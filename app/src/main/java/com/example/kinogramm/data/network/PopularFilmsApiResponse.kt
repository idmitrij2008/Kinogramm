package com.example.kinogramm.data.network


import com.example.kinogramm.data.models.FilmModel
import com.google.gson.annotations.SerializedName

data class PopularFilmsApiResponse(
    @SerializedName("results")
    val films: List<FilmModel>
)
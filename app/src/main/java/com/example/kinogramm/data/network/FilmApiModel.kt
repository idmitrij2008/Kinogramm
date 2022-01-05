package com.example.kinogramm.data.network


import com.google.gson.annotations.SerializedName

data class FilmApiModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String
)
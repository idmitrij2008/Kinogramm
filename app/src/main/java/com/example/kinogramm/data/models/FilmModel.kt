package com.example.kinogramm.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinogramm.util.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.FILMS_TABLE)
data class FilmModel(
    @PrimaryKey(autoGenerate = true)
    val filmId: Int = 0,
    @SerializedName("id")
    val remoteId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
)
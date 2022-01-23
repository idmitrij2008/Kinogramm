package com.example.kinogramm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinogramm.util.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.FILMS_TABLE)
data class FilmModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    val isLiked: Boolean = false
)
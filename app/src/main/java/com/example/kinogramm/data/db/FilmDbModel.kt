package com.example.kinogramm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinogramm.util.Constants

@Entity(tableName = Constants.FILMS_TABLE)
data class FilmDbModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val isLiked: Boolean = false
)

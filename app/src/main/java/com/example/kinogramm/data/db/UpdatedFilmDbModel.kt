package com.example.kinogramm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UpdatedFilmDbModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String
)

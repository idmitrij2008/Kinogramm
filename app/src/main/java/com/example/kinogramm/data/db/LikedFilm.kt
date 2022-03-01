package com.example.kinogramm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_films_table")
data class LikedFilm(
    @PrimaryKey(autoGenerate = false)
    val remoteId: Int
)
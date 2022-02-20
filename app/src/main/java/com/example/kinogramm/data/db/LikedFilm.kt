package com.example.kinogramm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinogramm.util.Constants

@Entity(tableName = Constants.LIKED_FILMS_TABLE)
data class LikedFilm(
    @PrimaryKey(autoGenerate = false)
    val remoteId: Int
)
package com.example.kinogramm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scheduled_films_table")
data class ScheduledFilm(
    @PrimaryKey(autoGenerate = false)
    val remoteId: Int
)
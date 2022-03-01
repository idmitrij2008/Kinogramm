package com.example.kinogramm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_table")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val filmId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
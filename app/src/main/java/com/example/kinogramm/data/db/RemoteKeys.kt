package com.example.kinogramm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinogramm.util.Constants

@Entity(tableName = Constants.REMOTE_KEYS_TABLE)
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val filmId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
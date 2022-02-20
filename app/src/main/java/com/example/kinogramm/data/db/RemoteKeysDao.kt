package com.example.kinogramm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys_table WHERE filmId = :filmId")
    suspend fun remoteKeysFilmId(filmId: Int): RemoteKeys?

    @Query("DELETE FROM remote_keys_table")
    suspend fun clearRemoteKeys()
}
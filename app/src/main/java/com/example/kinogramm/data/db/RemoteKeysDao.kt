package com.example.kinogramm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys_table WHERE filmId = :repoId")
    suspend fun remoteKeysFilmId(repoId: Long): RemoteKeys?

    @Query("DELETE FROM remote_keys_table")
    suspend fun clearRemoteKeys()
}
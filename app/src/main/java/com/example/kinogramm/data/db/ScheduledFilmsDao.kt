package com.example.kinogramm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScheduledFilmsDao {
    @Insert
    suspend fun insert(film: ScheduledFilm)

    @Delete
    suspend fun delete(film: ScheduledFilm)

    @Query("SELECT * FROM scheduled_films_table WHERE remoteId = :remoteId")
    suspend fun getByRemoteId(remoteId: Int): ScheduledFilm

    @Query("SELECT * FROM scheduled_films_table")
    fun getAll(): LiveData<List<ScheduledFilm>>

    @Query("DELETE FROM scheduled_films_table")
    suspend fun clear()
}
package com.example.kinogramm.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScheduledFilmsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
package com.example.kinogramm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LikedFilmsDao {
    @Insert
    suspend fun insert(film: LikedFilms)

    @Delete
    suspend fun delete(film: LikedFilms)

    @Query("SELECT * FROM liked_films_table")
    fun getAll(): LiveData<List<LikedFilms>>

    @Query("DELETE FROM liked_films_table")
    suspend fun clear()
}
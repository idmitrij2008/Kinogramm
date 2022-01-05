package com.example.kinogramm.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinogramm.data.FilmModel

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(filmDbModels: List<FilmModel>)

    @Query("DELETE FROM films_table")
    suspend fun clearFilms()

    @Query("SELECT * FROM films_table")
    fun getFilmsPagingSource(): PagingSource<Int, FilmModel>

    @Query("SELECT * FROM films_table")
    fun getFilmsList(): List<FilmModel>

    @Query("SELECT * FROM films_table WHERE filmId=:id")
    fun getFilm(id: Int): FilmModel
}
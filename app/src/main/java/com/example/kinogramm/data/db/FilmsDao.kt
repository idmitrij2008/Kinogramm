package com.example.kinogramm.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinogramm.data.models.FilmModel
import io.reactivex.rxjava3.core.Single

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(filmDbModels: List<FilmModel>): Single<List<Long>>

    @Query("DELETE FROM films_table")
    fun clearFilms(): Single<Int>

    @Query("SELECT * FROM films_table")
    fun getFilmsPagingSource(): PagingSource<Int, FilmModel>

    @Query("SELECT * FROM films_table")
    fun getFilmsList(): Single<List<FilmModel>>

    @Query("SELECT * FROM films_table WHERE remoteId = :remoteId")
    fun getFilm(remoteId: Int): Single<FilmModel>
}
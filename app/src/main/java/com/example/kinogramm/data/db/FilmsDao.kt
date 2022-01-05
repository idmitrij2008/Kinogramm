package com.example.kinogramm.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilms(filmDbModels: List<FilmDbModel>)

    @Query("SELECT * FROM films_table")
    fun getFilmsList(): LiveData<List<FilmDbModel>>

    @Update
    fun updateFilm(mapEntityToDbModel: FilmDbModel)

    @Query("SELECT * FROM films_table WHERE id=:id")
    fun getFilm(id: Int): LiveData<FilmDbModel>

    @Update(entity = FilmDbModel::class)
    fun refreshFilms(filmDbModels: List<UpdatedFilmDbModel>)

    @Query("SELECT COUNT(*) FROM films_table")
    fun getCount(): Int

    @Query("SELECT * FROM films_table WHERE isLiked=1")
    fun getLikedFilmsList(): LiveData<List<FilmDbModel>>
}
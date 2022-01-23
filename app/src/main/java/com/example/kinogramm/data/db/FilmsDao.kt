package com.example.kinogramm.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kinogramm.data.FilmModel

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(filmDbModels: List<FilmModel>)

    @Query("DELETE FROM films_table")
    suspend fun clearFilms()

    @Query("SELECT * FROM films_table")
    fun getFilmsList(): LiveData<List<FilmModel>>

    @Update
    fun updateFilm(mapEntityToDbModel: FilmModel)

    @Query("SELECT * FROM films_table WHERE id=:id")
    fun getFilm(id: Int): LiveData<FilmModel>

    @Update(entity = FilmModel::class)
    fun refreshFilms(filmDbModels: List<UpdatedFilmDbModel>)

    @Query("SELECT COUNT(*) FROM films_table")
    fun getCount(): Int

    @Query("SELECT * FROM films_table WHERE isLiked=1")
    fun getLikedFilmsList(): LiveData<List<FilmModel>>
}
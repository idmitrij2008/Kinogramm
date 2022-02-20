package com.example.kinogramm.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface LikedFilmsDao {
    @Insert
    fun insert(film: LikedFilm): Single<Long>

    @Delete
    fun delete(film: LikedFilm): Single<Int>

    @Query("SELECT * FROM liked_films_table")
    fun getAll(): Observable<List<LikedFilm>>
}
package com.example.kinogramm.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData

interface IFilmsRepository {
    fun getFilms(): List<Film>
    suspend fun getFilm(remoteId: Int): Film
    fun getPagedFilms(): LiveData<PagingData<Film>>

    suspend fun like(remoteId: Int)
    suspend fun unLike(remoteId: Int)
    fun getLikedFilms(): LiveData<List<Int>>

    suspend fun addScheduledFilm(remoteId: Int)
}
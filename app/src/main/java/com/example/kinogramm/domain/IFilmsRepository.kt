package com.example.kinogramm.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData

interface IFilmsRepository {
    fun getLikedFilmsLD(): LiveData<List<Film>>
    fun getFilmsListLD(): LiveData<Result<List<Film>>>
    fun getFilmLD(id: Int): LiveData<Film>
    fun invertIsLikedFor(film: Film)
    fun refreshFilms()

    fun getFilms(): LiveData<PagingData<List<Film>>>
}
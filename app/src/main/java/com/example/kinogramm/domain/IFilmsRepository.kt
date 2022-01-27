package com.example.kinogramm.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData

interface IFilmsRepository {
    fun getLikedFilmsLD(): LiveData<List<Film>>
    fun getFilmLD(id: Int): LiveData<Film>
    fun invertIsLikedFor(film: Film)
    fun getFilms(): LiveData<PagingData<Film>>
}
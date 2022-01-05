package com.example.kinogramm.domain

import androidx.lifecycle.LiveData

interface IFilmsRepository {
    fun getLikedFilmsLD(): LiveData<List<Film>>
    fun getFilmsListLD(): LiveData<Result<List<Film>>>
    fun getFilmLD(id: Int): LiveData<Film>
    fun invertIsLikedFor(film: Film)
    fun refreshFilms()
}
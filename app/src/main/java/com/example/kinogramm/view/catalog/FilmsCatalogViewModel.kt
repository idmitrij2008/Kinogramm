package com.example.kinogramm.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import com.example.kinogramm.domain.usecases.GetPagingFilmsUseCase

class FilmsCatalogViewModel(repository: IFilmsRepository) : ViewModel() {
    //    private val refreshFilmsUseCase = RefreshFilmsUseCase(repository)
    private val getPagingFilmsUseCase = GetPagingFilmsUseCase(repository)

    val films = getPagingFilmsUseCase.getFilms()

    private var lastClickedFilm: Film? = null

    private val _showDetailsForFilm = MutableLiveData<Film>()
    val showDetailsForFilm: LiveData<Film>
        get() = _showDetailsForFilm

    fun showDetailsFor(film: Film) {
        _showDetailsForFilm.value = film
        lastClickedFilm = film
    }

//    fun refreshFilms() {
//        refreshFilmsUseCase.refreshFilms()
//    }
}
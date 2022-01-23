package com.example.kinogramm.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import com.example.kinogramm.domain.usecases.GetFilmsUseCase
import com.example.kinogramm.domain.usecases.RefreshFilmsUseCase

class FilmsCatalogViewModel(repository: IFilmsRepository) : ViewModel() {
    private val getFilmUseCase = GetFilmsUseCase(repository)
    private val refreshFilmsUseCase = RefreshFilmsUseCase(repository)

    val films = getFilmUseCase.getFilmsList()

    private var lastClickedFilm: Film? = null

    private val _showDetailsForFilm = MutableLiveData<Film>()
    val showDetailsForFilm: LiveData<Film>
        get() = _showDetailsForFilm

    fun showDetailsFor(film: Film) {
        _showDetailsForFilm.value = film
        lastClickedFilm = film
    }

    fun refreshFilms() {
        refreshFilmsUseCase.refreshFilms()
    }
}
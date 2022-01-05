package com.example.kinogramm.view.catalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kinogramm.data.FilmsRepositoryImpl
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.usecases.GetFilmsUseCase
import com.example.kinogramm.domain.usecases.RefreshFilmsUseCase

class FilmsCatalogViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FilmsRepositoryImpl(application)
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
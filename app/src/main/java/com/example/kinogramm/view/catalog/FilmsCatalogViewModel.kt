package com.example.kinogramm.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava3.cachedIn
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.usecases.GetPagingFilmsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FilmsCatalogViewModel @Inject constructor(
    private val getPagingFilmsUseCase: GetPagingFilmsUseCase
) : ViewModel() {
    val films = getPagingFilmsUseCase.getPagedFilms().cachedIn(viewModelScope)

    private var lastClickedFilm: Film? = null

    private val _showDetailsForFilm = MutableLiveData<Film>()
    val showDetailsForFilm: LiveData<Film>
        get() = _showDetailsForFilm

    fun showDetailsFor(film: Film) {
        _showDetailsForFilm.value = film
        lastClickedFilm = film
    }
}
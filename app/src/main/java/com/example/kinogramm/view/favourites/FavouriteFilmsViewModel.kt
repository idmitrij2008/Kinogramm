package com.example.kinogramm.view.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import com.example.kinogramm.domain.usecases.GetLikedFilmsUseCase
import com.example.kinogramm.domain.usecases.LikeFilmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteFilmsViewModel(repository: IFilmsRepository) : ViewModel() {
    private val getLikedFilmsUseCase = GetLikedFilmsUseCase(repository)
    private val likeFilmUseCase = LikeFilmUseCase(repository)

    val favouriteFilms: LiveData<List<Film>> = getLikedFilmsUseCase.getLikedFilmsLD()

    fun removeFromFavourites(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            likeFilmUseCase.changeIsLikedState(film)
        }
    }
}
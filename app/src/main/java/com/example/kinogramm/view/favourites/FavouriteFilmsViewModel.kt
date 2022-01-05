package com.example.kinogramm.view.favourites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kinogramm.data.FilmsRepositoryImpl
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.usecases.GetLikedFilmsUseCase
import com.example.kinogramm.domain.usecases.LikeFilmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteFilmsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FilmsRepositoryImpl(application)
    private val getLikedFilmsUseCase = GetLikedFilmsUseCase(repository)
    private val likeFilmUseCase = LikeFilmUseCase(repository)

    val favouriteFilms: LiveData<List<Film>> = getLikedFilmsUseCase.getLikedFilmsLD()

    fun removeFromFavourites(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            likeFilmUseCase.changeIsLikedState(film)
        }
    }
}
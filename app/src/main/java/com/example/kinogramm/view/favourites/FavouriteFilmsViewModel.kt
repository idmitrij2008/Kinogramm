package com.example.kinogramm.view.favourites

import androidx.lifecycle.*
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.usecases.GetFilmsUseCase
import com.example.kinogramm.domain.usecases.GetLikedFilmsUseCase
import com.example.kinogramm.domain.usecases.LikeFilmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteFilmsViewModel @Inject constructor(
    private val getLikedFilmsUseCase: GetLikedFilmsUseCase,
    private val getFilmsUseCase: GetFilmsUseCase,
    private val likeFilmUseCase: LikeFilmUseCase,
) : ViewModel() {
    private val favouriteFilmIds: LiveData<List<Int>> = getLikedFilmsUseCase.getLikedFilms()

    private val favIdsObserver: Observer<List<Int>> by lazy {
        Observer { favs ->
            viewModelScope.launch(Dispatchers.IO) {
                val films = getFilmsUseCase.getFilmsList().filter { it.remoteId in favs }
                _favouriteFilms.postValue(films)
            }
        }
    }

    private val _favouriteFilms = MutableLiveData<List<Film>>()
    val favouriteFilms: LiveData<List<Film>>
        get() = _favouriteFilms

    init {
        favouriteFilmIds.observeForever(favIdsObserver)
    }

    override fun onCleared() {
        super.onCleared()
        favouriteFilmIds.removeObserver(favIdsObserver)
    }

    fun removeFromFavourites(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            likeFilmUseCase.unLikeFilm(film)
        }
    }
}
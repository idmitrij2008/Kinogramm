package com.example.kinogramm.view.favourites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.usecases.GetFilmsUseCase
import com.example.kinogramm.domain.usecases.GetLikedFilmsUseCase
import com.example.kinogramm.domain.usecases.LikeFilmUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "FavouriteFilmsViewModel"

class FavouriteFilmsViewModel @Inject constructor(
    getLikedFilmsUseCase: GetLikedFilmsUseCase,
    private val getFilmsUseCase: GetFilmsUseCase,
    private val likeFilmUseCase: LikeFilmUseCase,
) : ViewModel() {
    private val favouriteFilmIds: Observable<List<Int>> = getLikedFilmsUseCase.getLikedFilms()

    private val _favouriteFilms = MutableLiveData<List<Film>>()
    val favouriteFilms: LiveData<List<Film>>
        get() = _favouriteFilms

    init {
        favouriteFilmIds
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { favs ->
                getFavouriteFilms(favs)
            }
    }

    private fun getFavouriteFilms(favs: List<Int>) {
        getFilmsUseCase.getFilmsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ films ->
                _favouriteFilms.value = films.filter { it.remoteId in favs }
            }, { e ->
                Log.d(TAG, "${e.message}")
            })
    }

    fun removeFromFavourites(film: Film) {
        likeFilmUseCase.unLikeFilm(film)
    }
}
package com.example.kinogramm.domain.usecases

import android.util.Log
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "LikeFilmUseCase"

class LikeFilmUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {

    fun likeFilm(film: Film) {
        repository.like(film.remoteId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {},
                { Log.e(TAG, "${it.message}") }
            )
    }

    fun unLikeFilm(film: Film) {
        repository.unLike(film.remoteId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {},
                { Log.e(TAG, "${it.message}") }
            )
    }
}
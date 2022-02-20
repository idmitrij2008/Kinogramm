package com.example.kinogramm.domain.usecases

import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {
    fun getFilm(remoteId: Int): Single<Film> {
        return repository.getFilm(remoteId)
    }
}
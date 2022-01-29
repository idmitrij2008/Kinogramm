package com.example.kinogramm.domain.usecases

import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository

class LikeFilmUseCase(private val repository: IFilmsRepository) {

    suspend fun likeFilm(film: Film) {
        repository.like(film.remoteId)
    }

    suspend fun unLikeFilm(film: Film) {
        repository.unLike(film.remoteId)
    }
}
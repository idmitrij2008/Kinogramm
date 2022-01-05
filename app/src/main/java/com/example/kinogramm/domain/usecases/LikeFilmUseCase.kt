package com.example.kinogramm.domain.usecases

import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository

class LikeFilmUseCase(private val repository: IFilmsRepository) {
    fun changeIsLikedState(film: Film) {
        repository.invertIsLikedFor(film)
    }
}
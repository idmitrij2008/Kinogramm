package com.example.kinogramm.domain.usecases

import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository

class GetFilmUseCase(private val repository: IFilmsRepository) {
    suspend fun getFilm(remoteId: Int): Film {
        return repository.getFilm(remoteId)
    }
}
package com.example.kinogramm.domain.usecases

import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {
    suspend fun getFilm(remoteId: Int): Film {
        return repository.getFilm(remoteId)
    }
}
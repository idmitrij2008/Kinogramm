package com.example.kinogramm.domain.usecases

import com.example.kinogramm.domain.IFilmsRepository

class AddScheduledFilmUseCase(private val repository: IFilmsRepository) {
    suspend fun addScheduledFilm(remoteId: Int) {
        repository.addScheduledFilm(remoteId)
    }
}
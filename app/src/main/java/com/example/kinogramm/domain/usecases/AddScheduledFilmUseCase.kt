package com.example.kinogramm.domain.usecases

import com.example.kinogramm.domain.IFilmsRepository
import javax.inject.Inject

class AddScheduledFilmUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {
    suspend fun addScheduledFilm(remoteId: Int) {
        repository.addScheduledFilm(remoteId)
    }
}
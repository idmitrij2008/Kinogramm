package com.example.kinogramm.domain.usecases

import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import javax.inject.Inject

class GetFilmsUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {
    fun getFilmsList(): List<Film> {
        return repository.getFilms()
    }
}
package com.example.kinogramm.domain.usecases

import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository

class GetFilmsUseCase(private val repository: IFilmsRepository) {
    fun getFilmsList(): List<Film> {
        return repository.getFilms()
    }
}
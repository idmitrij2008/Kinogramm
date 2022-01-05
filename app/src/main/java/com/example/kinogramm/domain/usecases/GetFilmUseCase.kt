package com.example.kinogramm.domain.usecases

import androidx.lifecycle.LiveData
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository

class GetFilmUseCase(private val repository: IFilmsRepository) {
    fun getFilmLD(id: Int): LiveData<Film> {
        return repository.getFilmLD(id)
    }
}
package com.example.kinogramm.domain.usecases

import androidx.lifecycle.LiveData
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository

class GetLikedFilmsUseCase(private val repository: IFilmsRepository) {
    fun getLikedFilmsLD(): LiveData<List<Film>> {
        return repository.getLikedFilmsLD()
    }
}
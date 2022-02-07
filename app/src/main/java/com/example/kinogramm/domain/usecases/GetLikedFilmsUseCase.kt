package com.example.kinogramm.domain.usecases

import androidx.lifecycle.LiveData
import com.example.kinogramm.domain.IFilmsRepository

class GetLikedFilmsUseCase(private val repository: IFilmsRepository) {

    fun getLikedFilms(): LiveData<List<Int>> {
        return repository.getLikedFilms()
    }
}
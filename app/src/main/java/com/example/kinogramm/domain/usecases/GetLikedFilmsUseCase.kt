package com.example.kinogramm.domain.usecases

import androidx.lifecycle.LiveData
import com.example.kinogramm.domain.IFilmsRepository
import javax.inject.Inject

class GetLikedFilmsUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {

    fun getLikedFilms(): LiveData<List<Int>> {
        return repository.getLikedFilms()
    }
}
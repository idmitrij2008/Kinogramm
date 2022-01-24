package com.example.kinogramm.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository

class GetPagingFilmsUseCase(private val repository: IFilmsRepository) {
    fun getFilms(): LiveData<PagingData<Film>> {
        return repository.getFilms()
    }
}
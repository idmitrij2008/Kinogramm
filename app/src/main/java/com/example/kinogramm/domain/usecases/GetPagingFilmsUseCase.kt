package com.example.kinogramm.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import javax.inject.Inject

class GetPagingFilmsUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {
    fun getPagedFilms(): LiveData<PagingData<Film>> {
        return repository.getPagedFilms()
    }
}
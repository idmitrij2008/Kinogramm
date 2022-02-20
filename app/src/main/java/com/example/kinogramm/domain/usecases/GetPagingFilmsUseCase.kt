package com.example.kinogramm.domain.usecases

import androidx.paging.PagingData
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class GetPagingFilmsUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {
    fun getPagedFilms(): Flowable<PagingData<Film>> {
        return repository.getPagedFilms()
    }
}
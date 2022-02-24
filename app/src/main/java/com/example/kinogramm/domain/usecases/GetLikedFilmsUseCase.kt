package com.example.kinogramm.domain.usecases

import com.example.kinogramm.Mockable
import com.example.kinogramm.domain.IFilmsRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@Mockable
class GetLikedFilmsUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {

    fun getLikedFilms(): Observable<List<Int>> {
        return repository.getLikedFilms()
    }
}
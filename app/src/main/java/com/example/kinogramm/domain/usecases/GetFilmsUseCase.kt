package com.example.kinogramm.domain.usecases

import com.example.kinogramm.Mockable
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@Mockable
class GetFilmsUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {
    fun getFilmsList(): Single<List<Film>> {
        return repository.getFilms()
    }
}
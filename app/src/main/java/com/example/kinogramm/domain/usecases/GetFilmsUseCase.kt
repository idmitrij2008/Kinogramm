package com.example.kinogramm.domain.usecases

import androidx.lifecycle.LiveData
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import com.example.kinogramm.domain.Result

class GetFilmsUseCase(private val repository: IFilmsRepository) {
    fun getFilmsList(): LiveData<Result<List<Film>>> {
        return repository.getFilmsListLD()
    }
}
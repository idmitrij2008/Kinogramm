package com.example.kinogramm.domain.usecases

import android.util.Log
import com.example.kinogramm.domain.IFilmsRepository
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "AddScheduledFilmUseCase"

class AddScheduledFilmUseCase @Inject constructor(
    private val repository: IFilmsRepository
) {
    fun addScheduledFilm(remoteId: Int) {
        repository.addScheduledFilm(remoteId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {},
                { Log.e(TAG, "${it.message}") }
            )
    }
}
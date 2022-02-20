package com.example.kinogramm.domain

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface IFilmsRepository {
    fun getFilms(): Single<List<Film>>
    fun getFilm(remoteId: Int): Single<Film>
    fun getPagedFilms(): Flowable<PagingData<Film>>
    fun getLikedFilms(): Observable<List<Int>>
    fun addScheduledFilm(remoteId: Int): Single<Long>
    fun like(remoteId: Int): Single<Long>
    fun unLike(remoteId: Int): Single<Int>
}
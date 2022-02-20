package com.example.kinogramm.data

import androidx.paging.*
import androidx.paging.rxjava3.flowable
import com.example.kinogramm.data.db.*
import com.example.kinogramm.data.mapping.FilmMapper
import com.example.kinogramm.data.network.FilmsApi
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

private const val TAG = "FilmsRepository"
private const val NETWORK_PAGE_SIZE = 30

@ExperimentalPagingApi
class FilmsRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val filmsApi: FilmsApi,
    private val filmsDao: FilmsDao,
    private val likedFilmsDao: LikedFilmsDao,
    private val scheduledFilmsDao: ScheduledFilmsDao,
    private val filmMapper: FilmMapper
) : IFilmsRepository {

    override fun getFilms(): Single<List<Film>> {
        return filmsDao.getFilmsList().map {
            filmMapper.mapListModelToListEntity(it)
        }
    }

    override fun getFilm(remoteId: Int): Single<Film> {
        return filmsDao.getFilm(remoteId).map { model ->
            filmMapper.mapModelToEntity(model)
        }
    }

    override fun getPagedFilms(): Flowable<PagingData<Film>> {
        val pagingSourceFactory = {
            filmsDao.getFilmsPagingSource()
        }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = FilmsRemoteMediator(
                appDatabase = appDatabase,
                apiService = filmsApi
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flowable.map { pagingData ->
            pagingData.map { filmMapper.mapModelToEntity(it) }
        }
    }

    override fun like(remoteId: Int): Single<Long> {
        return likedFilmsDao.insert(LikedFilm(remoteId))
    }

    override fun unLike(remoteId: Int): Single<Int> {
        return likedFilmsDao.delete(LikedFilm(remoteId))
    }

    override fun getLikedFilms(): Observable<List<Int>> =
        likedFilmsDao.getAll().map { list ->
            list.map { it.remoteId }
        }

    override fun addScheduledFilm(remoteId: Int): Single<Long> {
        return scheduledFilmsDao.insert(ScheduledFilm(remoteId))
    }
}


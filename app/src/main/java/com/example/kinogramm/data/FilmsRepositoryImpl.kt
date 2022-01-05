package com.example.kinogramm.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import androidx.paging.*
import com.example.kinogramm.data.db.AppDatabase
import com.example.kinogramm.data.db.LikedFilms
import com.example.kinogramm.data.network.FilmsApi
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository

private const val TAG = "FilmsRepository"
private const val NETWORK_PAGE_SIZE = 30

@ExperimentalPagingApi
class FilmsRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val filmsApi: FilmsApi
) : IFilmsRepository {
    private val filmsDao = appDatabase.filmsDao()
    private val likedFilmsDao = appDatabase.likedFilmsDao()
    private val filmMapper = FilmMapper()

    override fun getFilms(): List<Film> {
        return filmMapper.mapListModelToListEntity(filmsDao.getFilmsList())
    }

    override suspend fun getFilm(id: Int): Film {
        return filmMapper.mapModelToEntity(filmsDao.getFilm(id))
    }

    override fun getPagedFilms(): LiveData<PagingData<Film>> {
        val pagingSourceFactory = {
            appDatabase.filmsDao().getFilmsPagingSource()
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
        ).liveData.map { pagingData ->
            pagingData.map { filmMapper.mapModelToEntity(it) }
        }
    }

    override suspend fun like(remoteId: Int) {
        likedFilmsDao.insert(LikedFilms(remoteId))
    }

    override suspend fun unLike(remoteId: Int) {
        likedFilmsDao.delete(LikedFilms(remoteId))
    }

    override fun getLikedFilms(): LiveData<List<Int>> =
        Transformations.map(likedFilmsDao.getAll()) { likedFilms ->
            likedFilms.map { it.remoteId }
        }
}


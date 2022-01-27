package com.example.kinogramm.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import androidx.paging.*
import com.example.kinogramm.data.db.AppDatabase
import com.example.kinogramm.data.network.FilmsApi
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository

private const val TAG = "FilmsRepository"
private const val NETWORK_PAGE_SIZE = 30

@ExperimentalPagingApi
class FilmsRepositoryImpl(
    private val application: Application,
    private val appDatabase: AppDatabase,
    private val filmsApi: FilmsApi
) : IFilmsRepository {
    private val filmsDao = appDatabase.filmsDao()
    private val filmMapper = FilmMapper()

    override fun getLikedFilmsLD(): LiveData<List<Film>> =
        Transformations.map(filmsDao.getLikedFilmsList()) {
            filmMapper.mapListModelToListEntity(it)
        }

    override fun getFilmLD(id: Int): LiveData<Film> =
        Transformations.map(filmsDao.getFilm(id)) {
            filmMapper.mapModelToEntity(it)
        }

    override fun invertIsLikedFor(film: Film) {
        filmsDao.updateFilm(filmMapper.mapEntityToModel(film).copy(isLiked = !film.isLiked))
    }

    override fun getFilms(): LiveData<PagingData<Film>> {
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

    private val hasInternetConnection: Boolean
        get() {
            val connectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
}


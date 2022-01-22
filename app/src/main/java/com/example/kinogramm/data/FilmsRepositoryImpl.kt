package com.example.kinogramm.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.kinogramm.data.db.AppDatabase
import com.example.kinogramm.data.db.FilmsDbMapper
import com.example.kinogramm.data.network.FilmsApiClient
import com.example.kinogramm.data.network.FilmsNetworkMapper
import com.example.kinogramm.domain.Film
import com.example.kinogramm.domain.IFilmsRepository
import com.example.kinogramm.domain.Result
import com.example.kinogramm.util.Constants.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "FilmsRepository"

class FilmsRepositoryImpl(private val application: Application) : IFilmsRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val filmsDao = AppDatabase.getInstance(application).filmsDao()
    private val dbMapper = FilmsDbMapper()
    private val networkMapper = FilmsNetworkMapper()

    private val filmsLD: LiveData<Result<List<Film>>> =
        Transformations.map(filmsDao.getFilmsList()) {
            Result.Success(dbMapper.mapListDbModelToListEntity(it))
        }

    override fun getLikedFilmsLD(): LiveData<List<Film>> =
        Transformations.map(filmsDao.getLikedFilmsList()) {
            dbMapper.mapListDbModelToListEntity(it)
        }

    override fun getFilmsListLD(): LiveData<Result<List<Film>>> {
        checkIfShouldUpdateDb()
        return filmsLD
    }

    private fun checkIfShouldUpdateDb() {
        coroutineScope.launch {
            if (filmsDao.getCount() == 0) {
                Log.d(TAG, "Local database is empty.")
                val filmsFromApi = getFilmsFromApi()
                if (filmsFromApi.isNotEmpty()) {
                    val films =
                        networkMapper.mapListNetworkModelToListEntity(filmsFromApi)
                    filmsDao.insertFilms(dbMapper.mapListEntityToDbModel(films))
                }
            } else {
                Log.d(TAG, "Fetching films from local database.")
            }
        }
    }

    override fun getFilmLD(id: Int): LiveData<Film> =
        Transformations.map(filmsDao.getFilm(id)) {
            dbMapper.mapDbModelToEntity(it)
        }

    override fun invertIsLikedFor(film: Film) {
        filmsDao.updateFilm(dbMapper.mapEntityToDbModel(film).copy(isLiked = !film.isLiked))
    }

    override fun refreshFilms() {
        coroutineScope.launch {
            Log.d(TAG, "Refreshing films list from network...")
            val filmsFromApi = getFilmsFromApi()
            val films =
                networkMapper.mapListNetworkModelToListEntity(filmsFromApi)
            if (filmsDao.getCount() == 0) {
                filmsDao.insertFilms(dbMapper.mapListEntityToDbModel(films))
            } else {
                filmsDao.refreshFilms(dbMapper.mapListEntityToUpdDbModel(films))
            }
        }
    }

    private suspend fun getFilmsFromApi() = withContext(Dispatchers.IO) {
        if (!hasInternetConnection) {
            Log.e(TAG, "Can't load films: no internet connection.")
            (filmsLD as MutableLiveData<Result<List<Film>>>).postValue(Result.Error("No internet connection."))
            return@withContext emptyList()
        }

        (filmsLD as MutableLiveData<Result<List<Film>>>).postValue(Result.Loading())
        Log.d(TAG, "Trying to load films from network...")
        val apiResponse = FilmsApiClient.instance.getPopularFilms(API_KEY)
        apiResponse.body()?.let { data ->
            Log.d(TAG, "${data.films.size} films loaded.")
            return@withContext data.films
        } ?: run {
            Log.d(TAG, "No films found.")
            filmsLD.value = Result.Error("No films found.")
            return@withContext emptyList()
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


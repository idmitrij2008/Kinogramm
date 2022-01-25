package com.example.kinogramm.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.kinogramm.data.db.AppDatabase
import com.example.kinogramm.data.db.RemoteKeys
import com.example.kinogramm.data.network.FilmsApi
import com.example.kinogramm.util.Constants
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
private const val TAG = "FilmsRemoteMediator"

@ExperimentalPagingApi
class FilmsRemoteMediator(
    private val apiService: FilmsApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, FilmModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmModel>
    ): MediatorResult {
        Log.d(TAG, "load start")
        Log.d(TAG, "loadType = ${loadType.name}")
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Log.d(TAG, "remoteKeys = $remoteKeys")
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                Log.d(TAG, "remoteKeys = $remoteKeys")
                val prevKey = remoteKeys?.prevKey
                Log.d(TAG, "prevKey = $prevKey")
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                Log.d(TAG, "remoteKeys = $remoteKeys")
                val nextKey = remoteKeys?.nextKey
                Log.d(TAG, "nextKey = $nextKey")
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        Log.d(TAG, "page = $page")

        try {
            val apiResponse = apiService.getPopularFilms(Constants.API_KEY, page).body()

            apiResponse
                ?: return MediatorResult.Error(RuntimeException("Received API data is null."))

            val films = apiResponse.films
            Log.d(TAG, "films size = ${films.size}")
            val endOfPaginationReached = films.isEmpty()
            Log.d(TAG, "endOfPaginationReached = $endOfPaginationReached")
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    Log.d(TAG, "Clear dbs")
                    appDatabase.remoteKeysDao().clearRemoteKeys()
                    appDatabase.filmsDao().clearFilms()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.d(TAG, "prevKey = $prevKey")
                Log.d(TAG, "nextKey = $nextKey")

                appDatabase.filmsDao().insertAll(films)
                val filmsFromDb = appDatabase.filmsDao().getFilmsList()
                val keys = filmsFromDb.map {
                    RemoteKeys(filmId = it.filmId, prevKey = prevKey, nextKey = nextKey)
                }
                Log.d(TAG, "remoteKeys = $keys")
                appDatabase.remoteKeysDao().insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FilmModel>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { film ->
                // Get the remote keys of the last item retrieved
                appDatabase.remoteKeysDao().remoteKeysFilmId(film.filmId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FilmModel>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { film ->
                // Get the remote keys of the first items retrieved
                appDatabase.remoteKeysDao().remoteKeysFilmId(film.filmId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FilmModel>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.filmId?.let { filmId ->
                appDatabase.remoteKeysDao().remoteKeysFilmId(filmId)
            }
        }
    }
}
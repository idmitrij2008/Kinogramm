package com.example.kinogramm.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.kinogramm.data.db.AppDatabase
import com.example.kinogramm.data.network.FilmsApi

@ExperimentalPagingApi
class FilmsRemoteMediator(
    private val apiService: FilmsApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, FilmModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmModel>
    ): MediatorResult {
        TODO("Not yet implemented")
    }
}
package com.example.kinogramm.view.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi

@ExperimentalPagingApi
class FilmDetailsViewModelFactory(private val application: Application, private val filmId: Int) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmDetailsViewModel::class.java)) {
            return FilmDetailsViewModel(application, filmId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.kinogramm.view.catalog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.Injection

@ExperimentalPagingApi
class FilmCatalogViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmsCatalogViewModel::class.java)) {
            return FilmsCatalogViewModel(Injection.provideFilmsRepository(application)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
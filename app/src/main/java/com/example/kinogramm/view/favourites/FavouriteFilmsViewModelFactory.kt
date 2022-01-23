package com.example.kinogramm.view.favourites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinogramm.di.Injection

class FavouriteFilmsViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteFilmsViewModel::class.java)) {
            return FavouriteFilmsViewModel(Injection.provideFilmsRepository(application)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
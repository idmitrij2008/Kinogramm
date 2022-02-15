package com.example.kinogramm.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.ViewModelKey
import com.example.kinogramm.view.details.FilmDetailsViewModel
import dagger.Binds
import dagger.Module

@Module
@ExperimentalPagingApi
interface FilmDetailsModule {

    @Binds
    @dagger.multibindings.IntoMap
    @ViewModelKey(FilmDetailsViewModel::class)
    fun bindFilmDetailsViewModel(viewModel: FilmDetailsViewModel): ViewModel
}
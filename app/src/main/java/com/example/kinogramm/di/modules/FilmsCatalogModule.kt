package com.example.kinogramm.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.ViewModelKey
import com.example.kinogramm.view.catalog.FilmsCatalogViewModel
import dagger.Binds
import dagger.Module

@Module
@ExperimentalPagingApi
interface FilmsCatalogModule {

    @Binds
    @dagger.multibindings.IntoMap
    @ViewModelKey(FilmsCatalogViewModel::class)
    fun bindFilmCatalogViewModel(viewModel: FilmsCatalogViewModel): ViewModel
}
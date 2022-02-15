package com.example.kinogramm.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.ViewModelKey
import com.example.kinogramm.view.MainViewModel
import com.example.kinogramm.view.catalog.FilmsCatalogViewModel
import com.example.kinogramm.view.details.FilmDetailsViewModel
import com.example.kinogramm.view.exit.ExitViewModel
import com.example.kinogramm.view.favourites.FavouriteFilmsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

//@Module
//@ExperimentalPagingApi
//interface ViewModelModule {
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(FavouriteFilmsViewModel::class)
//    fun bindFavouriteFilmsViewModel(viewModel: FavouriteFilmsViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ExitViewModel::class)
//    fun bindExitViewModel(viewModel: ExitViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(FilmDetailsViewModel::class)
//    fun bindFilmDetailsViewModel(viewModel: FilmDetailsViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(FilmsCatalogViewModel::class)
//    fun bindFilmCatalogViewModel(viewModel: FilmsCatalogViewModel): ViewModel
//}
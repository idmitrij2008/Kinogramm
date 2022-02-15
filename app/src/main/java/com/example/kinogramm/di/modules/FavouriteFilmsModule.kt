package com.example.kinogramm.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.ViewModelKey
import com.example.kinogramm.view.favourites.FavouriteFilmsViewModel
import dagger.Binds
import dagger.Module

@Module
@ExperimentalPagingApi
interface FavouriteFilmsModule {

    @Binds
    @dagger.multibindings.IntoMap
    @ViewModelKey(FavouriteFilmsViewModel::class)
    fun bindFavouriteFilmsViewModel(viewModel: FavouriteFilmsViewModel): ViewModel
}
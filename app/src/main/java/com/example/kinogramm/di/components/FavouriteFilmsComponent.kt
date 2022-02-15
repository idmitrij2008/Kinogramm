package com.example.kinogramm.di.components

import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.modules.FavouriteFilmsModule
import com.example.kinogramm.view.favourites.FavouriteFilmsFragment
import dagger.Subcomponent

@Subcomponent(modules = [FavouriteFilmsModule::class])
@ExperimentalPagingApi
interface FavouriteFilmsComponent {

    fun inject(fragment: FavouriteFilmsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): FavouriteFilmsComponent
    }
}
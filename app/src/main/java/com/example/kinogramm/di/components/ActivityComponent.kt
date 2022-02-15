package com.example.kinogramm.di.components

import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.modules.ActivityModule
import com.example.kinogramm.di.scopes.ActivityScope
import com.example.kinogramm.view.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
@ExperimentalPagingApi
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun filmCatalogComponentFactory(): FilmsCatalogComponent.Factory

    fun filmDetailsComponentFactory(): FilmDetailsComponent.Factory

    fun exitComponentFactory(): ExitComponent.Factory

    fun favouriteFilmsComponentFactory(): FavouriteFilmsComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }
}
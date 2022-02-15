package com.example.kinogramm.di.components

import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.FilmRemoteIdQualifier
import com.example.kinogramm.view.catalog.FilmsCatalogFragment
import com.example.kinogramm.view.details.FilmDetailsFragment
import com.example.kinogramm.view.exit.ExitDialogFragment
import com.example.kinogramm.view.favourites.FavouriteFilmsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
@ExperimentalPagingApi
interface FragmentComponent {

    fun inject(fragment: FavouriteFilmsFragment)

    fun inject(fragment: ExitDialogFragment)

    fun inject(fragment: FilmsCatalogFragment)

    fun inject(fragment: FilmDetailsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @FilmRemoteIdQualifier filmRemoteId: Int
        ): FragmentComponent
    }
}
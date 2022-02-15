package com.example.kinogramm.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.view.MainActivity
import com.example.kinogramm.view.catalog.FilmsCatalogFragment
import com.example.kinogramm.view.exit.ExitDialogFragment
import com.example.kinogramm.view.favourites.FavouriteFilmsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
@ExperimentalPagingApi
interface ApplicationComponent {

    fun filmDetailsComponentFactory(): FilmDetailsComponent.Factory

    fun inject(activity: MainActivity)

    fun inject(fragment: FavouriteFilmsFragment)

    fun inject(fragment: ExitDialogFragment)

    fun inject(fragment: FilmsCatalogFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}
package com.example.kinogramm.di.components

import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.modules.FilmsCatalogModule
import com.example.kinogramm.view.catalog.FilmsCatalogFragment
import dagger.Subcomponent

@Subcomponent(modules = [FilmsCatalogModule::class])
@ExperimentalPagingApi
interface FilmsCatalogComponent {

    fun inject(fragment: FilmsCatalogFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): FilmsCatalogComponent
    }
}
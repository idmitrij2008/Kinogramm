package com.example.kinogramm.di.components

import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.FilmRemoteIdQualifier
import com.example.kinogramm.di.modules.FilmDetailsModule
import com.example.kinogramm.view.details.FilmDetailsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [FilmDetailsModule::class])
@ExperimentalPagingApi
interface FilmDetailsComponent {

    fun inject(fragment: FilmDetailsFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance @FilmRemoteIdQualifier filmRemoteId: Int
        ): FilmDetailsComponent

    }
}
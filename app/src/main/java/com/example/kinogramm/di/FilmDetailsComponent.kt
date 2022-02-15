package com.example.kinogramm.di

import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.view.details.FilmDetailsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
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
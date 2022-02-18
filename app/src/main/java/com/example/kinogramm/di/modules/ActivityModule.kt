package com.example.kinogramm.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.ViewModelKey
import com.example.kinogramm.di.components.ExitComponent
import com.example.kinogramm.di.components.FavouriteFilmsComponent
import com.example.kinogramm.di.components.FilmDetailsComponent
import com.example.kinogramm.di.components.FilmsCatalogComponent
import com.example.kinogramm.view.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    subcomponents = (
            [
                ExitComponent::class,
                FavouriteFilmsComponent::class,
                FilmDetailsComponent::class,
                FilmsCatalogComponent::class
            ])
)
@ExperimentalPagingApi
interface ActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}
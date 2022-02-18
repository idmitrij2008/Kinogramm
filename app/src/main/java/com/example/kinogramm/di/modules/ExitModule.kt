package com.example.kinogramm.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.ViewModelKey
import com.example.kinogramm.view.exit.ExitViewModel
import dagger.Binds
import dagger.Module

@Module
@ExperimentalPagingApi
interface ExitModule {

    @Binds
    @dagger.multibindings.IntoMap
    @ViewModelKey(ExitViewModel::class)
    fun bindExitViewModel(viewModel: ExitViewModel): ViewModel
}
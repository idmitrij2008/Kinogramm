package com.example.kinogramm.di.components

import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.modules.ExitModule
import com.example.kinogramm.view.exit.ExitDialogFragment
import dagger.Subcomponent

@Subcomponent(modules = [ExitModule::class])
@ExperimentalPagingApi
interface ExitComponent {

    fun inject(fragment: ExitDialogFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ExitComponent
    }
}
package com.example.kinogramm.di.components

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.scopes.ApplicationScope
import com.example.kinogramm.di.modules.DataModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [DataModule::class]
)
@ExperimentalPagingApi
interface ApplicationComponent {

    fun activityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}
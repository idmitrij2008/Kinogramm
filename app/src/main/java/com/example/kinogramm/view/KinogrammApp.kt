package com.example.kinogramm.view

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.components.ApplicationComponent
import com.example.kinogramm.di.components.DaggerApplicationComponent

@ExperimentalPagingApi
class KinogrammApp : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}
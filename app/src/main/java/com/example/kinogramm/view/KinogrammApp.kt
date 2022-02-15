package com.example.kinogramm.view

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.di.ApplicationComponent
import com.example.kinogramm.di.DaggerApplicationComponent

@ExperimentalPagingApi
class KinogrammApp : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}
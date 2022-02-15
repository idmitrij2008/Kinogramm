package com.example.kinogramm.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.data.FilmsRepositoryImpl
import com.example.kinogramm.data.db.AppDatabase
import com.example.kinogramm.data.db.FilmsDao
import com.example.kinogramm.data.db.LikedFilmsDao
import com.example.kinogramm.data.db.ScheduledFilmsDao
import com.example.kinogramm.data.network.FilmsApi
import com.example.kinogramm.domain.IFilmsRepository
import com.example.kinogramm.util.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalPagingApi
@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepository(impl: FilmsRepositoryImpl): IFilmsRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideAppDatabase(
            application: Application
        ): AppDatabase {
            return AppDatabase.getInstance(application)
        }

        @Provides
        @ApplicationScope
        fun provideFilmsDao(
            appDatabase: AppDatabase
        ): FilmsDao {
            return appDatabase.filmsDao()
        }

        @Provides
        @ApplicationScope
        fun provideLikedFilmsDao(
            appDatabase: AppDatabase
        ): LikedFilmsDao {
            return appDatabase.likedFilmsDao()
        }

        @Provides
        @ApplicationScope
        fun provideScheduledFilmsDao(
            appDatabase: AppDatabase
        ): ScheduledFilmsDao {
            return appDatabase.scheduledFilmsDao()
        }

        @Provides
        @ApplicationScope
        fun provideOkHttp(): OkHttpClient {
            return OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @ApplicationScope
        fun provideGsonConverterFactory(): GsonConverterFactory {
            return GsonConverterFactory.create()
        }

        @Provides
        @ApplicationScope
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
        }

        @Provides
        @ApplicationScope
        fun provideFilmsApi(
            retrofit: Retrofit
        ): FilmsApi {
            return retrofit.create(FilmsApi::class.java)
        }
    }
}
package com.example.kinogramm.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.data.FilmsRepositoryImpl
import com.example.kinogramm.data.db.AppDatabase
import com.example.kinogramm.data.network.FilmsApi
import com.example.kinogramm.domain.IFilmsRepository
import com.example.kinogramm.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Injection {

    private var GSON_INSTANCE: GsonConverterFactory? = null
    private var OK_HTTP_INSTANCE: OkHttpClient? = null
    private var RETROFIT_INSTANCE: Retrofit? = null
    private var REPOSITORY_INSTANCE: IFilmsRepository? = null
    private var APP_DATABASE_INSTANCE: AppDatabase? = null

    private fun provideGsonConverterFactory(): GsonConverterFactory {
        return GSON_INSTANCE ?: GsonConverterFactory.create().apply {
            GSON_INSTANCE = this
        }
    }

    private fun provideOkHttp(): OkHttpClient {
        return OK_HTTP_INSTANCE ?: OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build().apply {
                OK_HTTP_INSTANCE = this
            }
    }

    private fun provideRetrofit(): Retrofit {
        return RETROFIT_INSTANCE ?: Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .client(provideOkHttp())
            .addConverterFactory(provideGsonConverterFactory())
            .build().apply {
                RETROFIT_INSTANCE = this
            }
    }

    private fun provideFilmsApiImpl(): FilmsApi = provideRetrofit().create(FilmsApi::class.java)

    private fun provideAppDatabase(application: Application): AppDatabase {
        return APP_DATABASE_INSTANCE ?: AppDatabase.getInstance(application).apply {
            APP_DATABASE_INSTANCE = this
        }
    }

    @ExperimentalPagingApi
    fun provideFilmsRepository(application: Application): IFilmsRepository {
        return REPOSITORY_INSTANCE ?: FilmsRepositoryImpl(
            provideAppDatabase(application),
            provideFilmsApiImpl()
        ).apply {
            REPOSITORY_INSTANCE = this
        }
    }
}
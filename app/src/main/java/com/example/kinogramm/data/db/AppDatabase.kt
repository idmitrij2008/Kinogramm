package com.example.kinogramm.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kinogramm.data.models.FilmModel
import com.example.kinogramm.util.Constants.DB_NAME

@Database(
    entities = [FilmModel::class, RemoteKeys::class, LikedFilm::class, ScheduledFilm::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmsDao(): FilmsDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun likedFilmsDao(): LikedFilmsDao
    abstract fun scheduledFilmsDao(): ScheduledFilmsDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }

                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = db
                return db
            }
        }
    }
}
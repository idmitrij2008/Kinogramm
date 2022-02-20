package com.example.kinogramm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import io.reactivex.rxjava3.core.Single

@Dao
interface ScheduledFilmsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(film: ScheduledFilm): Single<Long>
}
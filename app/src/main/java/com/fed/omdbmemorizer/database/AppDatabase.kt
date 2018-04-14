package com.fed.omdbmemorizer.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fed.omdbmemorizer.model.MovieDTO


@Database(entities = arrayOf(MovieDTO::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract val movieDAO: MovieDAO
}
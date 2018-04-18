package com.fed.omdbmemorizer.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fed.omdbmemorizer.data.entity.MovieEntity


@Database(entities = [(MovieEntity::class)], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract val movieDAO: MovieDAO
}
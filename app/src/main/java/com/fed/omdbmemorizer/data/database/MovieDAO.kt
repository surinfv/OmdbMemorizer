package com.fed.omdbmemorizer.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.fed.omdbmemorizer.data.entity.MovieEntity
import io.reactivex.Flowable


@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieEntity: MovieEntity)

    @Delete
    fun delete(movieEntity: MovieEntity)

    @Query("SELECT * FROM favoritesMovies")
    fun getFavoriteMovies(): Flowable<List<MovieEntity>>
}
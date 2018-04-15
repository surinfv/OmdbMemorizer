package com.fed.omdbmemorizer.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.fed.omdbmemorizer.model.MovieDbEntity
import io.reactivex.Flowable


@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieDbEntity)

    @Delete
    fun delete(movie: MovieDbEntity)

    @Query("SELECT * FROM favoritesMovies")
    fun getFavoriteMovies(): Flowable<List<MovieDbEntity>>
}
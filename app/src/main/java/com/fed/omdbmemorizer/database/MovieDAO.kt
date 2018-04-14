package com.fed.omdbmemorizer.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import com.fed.omdbmemorizer.model.MovieDTO


@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieDTO)

    @Delete
    fun delete(movie: MovieDTO)

//    @Query("SELECT * FROM favoritesMovies")
//    fun getFavoriteMovies(): Flowable<ArrayList<MovieDTO>>
}
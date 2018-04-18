package com.fed.omdbmemorizer.data.repository

import com.fed.omdbmemorizer.data.database.MovieDAO
import com.fed.omdbmemorizer.data.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable


class LocalMovieStore(private var movieDao: MovieDAO) {

    fun addFavorite(movieEntity: MovieEntity): Completable =
            Completable.fromAction {
                movieDao.insert(movieEntity)
            }

    fun removeFavorite(movieEntity: MovieEntity): Completable =
            Completable.fromAction {
                movieDao.delete(movieEntity)
            }

    fun loadFavorites(): Flowable<List<MovieEntity>> =
            movieDao.getFavoriteMovies()
}
package com.fed.omdbmemorizer.data.repository.local

import com.fed.omdbmemorizer.data.database.MovieDAO
import com.fed.omdbmemorizer.data.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable


class LocalMovieStore(private var movieDao: MovieDAO) : ILocalMovieStore {

    override fun addFavorite(movieEntity: MovieEntity): Completable =
            Completable.fromAction {
                movieDao.insert(movieEntity)
            }

    override fun removeFavorite(movieEntity: MovieEntity): Completable =
            Completable.fromAction {
                movieDao.delete(movieEntity)
            }

    override fun loadFavorites(): Flowable<List<MovieEntity>> =
            movieDao.getFavoriteMovies()
}
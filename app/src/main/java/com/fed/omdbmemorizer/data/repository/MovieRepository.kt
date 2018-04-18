package com.fed.omdbmemorizer.data.repository

import com.fed.omdbmemorizer.data.entity.MovieEntity
import com.fed.omdbmemorizer.presentation.model.MovieModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


class MovieRepository(private var localMovieStore : LocalMovieStore,
                      private var networkMovieStore: NetworkMovieStore) : MovieRepositoryInterface {

    override fun searchMovies(title: String, page: String): Single<List<MovieEntity>> =
        networkMovieStore.searchMovies(title, page)

    override fun addFavorite(movieEntity: MovieEntity): Completable =
        localMovieStore.addFavorite(movieEntity)

    override fun removeFavorite(movieEntity: MovieEntity): Completable =
        localMovieStore.removeFavorite(movieEntity)

    override fun loadFavorites(): Flowable<List<MovieEntity>> =
        localMovieStore.loadFavorites()
}
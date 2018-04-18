package com.fed.omdbmemorizer.domain.interactor.favoriteMovies

import com.fed.omdbmemorizer.domain.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable


interface IFavoriteMoviesInteractor {
    fun addFavorite(movie: Movie): Completable

    fun removeFavorite(movie: Movie): Completable

    fun loadFavorites(): Flowable<List<Movie>>
}
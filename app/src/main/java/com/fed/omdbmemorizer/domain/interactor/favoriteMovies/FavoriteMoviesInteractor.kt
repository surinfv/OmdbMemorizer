package com.fed.omdbmemorizer.domain.interactor.favoriteMovies

import com.fed.omdbmemorizer.data.repository.IMovieRepository
import com.fed.omdbmemorizer.domain.model.Movie
import com.fed.omdbmemorizer.domain.model.mapper.MovieMapper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class FavoriteMoviesInteractor(private var repository: IMovieRepository) : IFavoriteMoviesInteractor {

    private val mapper = MovieMapper()

    override fun addFavorite(movie: Movie): Completable =
            repository.addFavorite(mapper.fromMovieToEntity(movie))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    override fun removeFavorite(movie: Movie): Completable =
            repository.removeFavorite(mapper.fromMovieToEntity(movie))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())


    override fun loadFavorites(): Flowable<List<Movie>> =
            repository.loadFavorites()
                    .map { mapper.fromEntityListToMovieList(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
}
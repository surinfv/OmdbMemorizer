package com.fed.omdbmemorizer.domain.interactor.searchMovies

import com.fed.omdbmemorizer.data.repository.IMovieRepository
import com.fed.omdbmemorizer.domain.model.Movie
import com.fed.omdbmemorizer.domain.model.mapper.MovieMapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SearchMoviesInteractor(private var repository: IMovieRepository) : ISearchMoviesInteractor {

    private val mapper = MovieMapper()

    override fun searchMovies(title: String, page: String): Single<List<Movie>> =
            repository.searchMovies(title, page)
                    .map { mapper.fromEntityListToMovieList(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

}
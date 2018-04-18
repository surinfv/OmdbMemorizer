package com.fed.omdbmemorizer.domain.interactor.searchMovies

import com.fed.omdbmemorizer.domain.model.Movie
import io.reactivex.Single


interface ISearchMoviesInteractor {
    fun searchMovies(title: String, page: String): Single<List<Movie>>
}
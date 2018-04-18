package com.fed.omdbmemorizer.data.repository.net

import com.fed.omdbmemorizer.data.entity.MovieEntity
import io.reactivex.Single


interface INetworkMovieStore {
    fun searchMovies(title: String, page: String): Single<List<MovieEntity>>
}
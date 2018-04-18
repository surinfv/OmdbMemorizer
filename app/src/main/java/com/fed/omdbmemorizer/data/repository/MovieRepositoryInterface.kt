package com.fed.omdbmemorizer.data.repository

import com.fed.omdbmemorizer.data.entity.MovieEntity
import com.fed.omdbmemorizer.presentation.model.MovieModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface MovieRepositoryInterface {
    fun searchMovies(title: String, page: String): Single<List<MovieEntity>>
    fun addFavorite(movieEntity: MovieEntity): Completable
    fun removeFavorite(movieEntity: MovieEntity): Completable
    fun loadFavorites(): Flowable<List<MovieEntity>>
}
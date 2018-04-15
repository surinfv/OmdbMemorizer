package com.fed.omdbmemorizer.repository

import com.fed.omdbmemorizer.model.MovieUiEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface IRepository {
    fun searchMovies(title: String, page: String): Single<List<MovieUiEntity>>
    fun addFavorite(movieUI: MovieUiEntity): Completable
    fun removeFavorite(movieUI: MovieUiEntity): Completable
    fun loadFavorites(): Flowable<List<MovieUiEntity>>
}
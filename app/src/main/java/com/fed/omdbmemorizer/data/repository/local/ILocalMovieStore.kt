package com.fed.omdbmemorizer.data.repository.local

import com.fed.omdbmemorizer.data.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable


interface ILocalMovieStore {
    fun addFavorite(movieEntity: MovieEntity): Completable

    fun removeFavorite(movieEntity: MovieEntity): Completable

    fun loadFavorites(): Flowable<List<MovieEntity>>
}
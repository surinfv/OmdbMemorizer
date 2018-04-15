package com.fed.omdbmemorizer.repository

import com.fed.omdbmemorizer.model.MovieDTO
import com.fed.omdbmemorizer.model.ResponseDTO
import io.reactivex.Completable
import io.reactivex.Single


interface IRepository {
    fun searchMovies(title: String, page: String): Single<ResponseDTO>
    fun addFavorite(movie: MovieDTO): Completable
//    fun loadFavorites(): Flowable<ArrayList<MovieDTO>>
}
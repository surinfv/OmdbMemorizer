package com.fed.omdbmemorizer.repository

import com.fed.omdbmemorizer.database.MovieDAO
import com.fed.omdbmemorizer.model.MovieDTO
import com.fed.omdbmemorizer.model.ResponseDTO
import com.fed.omdbmemorizer.network.OmdbApi
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class Repository(var api: OmdbApi,
                 var movieDao: MovieDAO) : IRepository {

    override fun searchMovies(title: String, page: String): Single<ResponseDTO> =
            api.searchMovies(title, page)
//                .map {  }

    override fun addFavorite(movie: MovieDTO): Completable =
            Completable.fromAction { movieDao.insert(movie) }

//    override fun loadFavorites(): Flowable<ArrayList<MovieDTO>> =
//            movieDao.getFavoriteMovies()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())

}
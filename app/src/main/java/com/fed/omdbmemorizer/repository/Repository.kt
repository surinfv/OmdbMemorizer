package com.fed.omdbmemorizer.repository

import com.fed.omdbmemorizer.database.MovieDAO
import com.fed.omdbmemorizer.model.MovieDTO
import com.fed.omdbmemorizer.model.ResponseDTO
import com.fed.omdbmemorizer.network.OmdbApi
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class Repository(var api: OmdbApi, var movieDao: MovieDAO) : IRepository {

    override fun searchMovies(title: String): Single<ResponseDTO> {
        return api.searchMovies(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveFavorite(movie: MovieDTO) : Completable =
        Completable.fromAction { movieDao.insert(movie) }
                .subscribeOn(Schedulers.io())

//    override fun loadFavorites(): Flowable<ArrayList<MovieDTO>> =
//            movieDao.getFavoriteMovies()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())

}
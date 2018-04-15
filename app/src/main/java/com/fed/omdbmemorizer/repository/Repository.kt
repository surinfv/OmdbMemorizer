package com.fed.omdbmemorizer.repository

import com.fed.omdbmemorizer.database.Mapper
import com.fed.omdbmemorizer.database.MovieDAO
import com.fed.omdbmemorizer.model.MovieUiEntity
import com.fed.omdbmemorizer.network.OmdbApi
import io.reactivex.Completable
import io.reactivex.Single


class Repository(var api: OmdbApi,
                 var movieDao: MovieDAO,
                 var mapper: Mapper) : IRepository {

    override fun searchMovies(title: String, page: String): Single<List<MovieUiEntity>> =
            api.searchMovies(title, page)
                    .map { mapper.fromDTOtoUI(it.movieList) }

    override fun addFavorite(movieUI: MovieUiEntity): Completable =
            Completable.fromAction {
                movieDao.insert(mapper.fromUItoDB(movieUI))
            }

//    override fun loadFavorites(): Flowable<ArrayList<MovieDTO>> =
//            movieDao.getFavoriteMovies()
}
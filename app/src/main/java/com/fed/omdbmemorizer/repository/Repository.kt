package com.fed.omdbmemorizer.repository

import com.fed.omdbmemorizer.model.ResponseDTO
import com.fed.omdbmemorizer.network.OmdbApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class Repository(var api: OmdbApi) : IRepository {
    override fun fetchMoviesFromNet(title: String): Single<ResponseDTO> {
        return api.searchMovies(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
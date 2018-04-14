package com.fed.omdbmemorizer.repository

import com.fed.omdbmemorizer.model.ResponseDTO
import io.reactivex.Single


interface IRepository {
    fun fetchMoviesFromNet(title: String): Single<ResponseDTO>
}
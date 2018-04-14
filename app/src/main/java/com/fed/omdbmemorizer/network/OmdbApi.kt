package com.fed.omdbmemorizer.network

import com.fed.omdbmemorizer.model.ResponseDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface OmdbApi {
    @GET("/?apikey=79362265")
    fun searchMovies(@Query("s") title: String,
                     @Query("page") page: String): Single<ResponseDTO>
}
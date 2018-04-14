package com.fed.omdbmemorizer.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {
    private val API_KEY = "79362265"
    private val BASE_URL = "http://www.omdbapi.com/?apikey=$API_KEY]&"

    private val retrofitInstance: Retrofit
        get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

//    val apiService: ApiService
//        get() = retrofitInstance.create(ApiService::class.java)
}
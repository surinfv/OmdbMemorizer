package com.fed.omdbmemorizer.data.repository.net

import com.fed.omdbmemorizer.data.entity.MovieEntity
import com.fed.omdbmemorizer.data.entity.mapper.IMovieEntityMapper
import com.fed.omdbmemorizer.data.entity.mapper.MovieEntityMapper
import com.fed.omdbmemorizer.data.network.OmdbApi
import io.reactivex.Single


class NetworkMovieStore(private var api: OmdbApi, private var mapper: IMovieEntityMapper) : INetworkMovieStore {

    override fun searchMovies(title: String, page: String): Single<List<MovieEntity>> =
            api.searchMovies(title, page)
                    .map {
                        if (it.movieList != null) {
                            mapper.fromDTOListToEntityList(it.movieList)
                        } else {
                            ArrayList()
                        }
                    }
}
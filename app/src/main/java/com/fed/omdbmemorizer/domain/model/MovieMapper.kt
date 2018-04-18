package com.fed.omdbmemorizer.domain.model

import com.fed.omdbmemorizer.data.entity.MovieEntity


class MovieMapper {
    fun fromEntityListToMovieList(listEntity: List<MovieEntity>?): List<Movie> {
        val listMovie = ArrayList<Movie>()
        if (listEntity != null) {
            for (movieEntity in listEntity) {
                listMovie.add(Movie(movieEntity.title,
                        movieEntity.year,
                        movieEntity.poster
                ))
            }
        }
        return listMovie
    }

    fun fromMovieToEntity(movie: Movie): MovieEntity =
            MovieEntity(movie.title,
                    movie.year,
                    movie.poster)
}
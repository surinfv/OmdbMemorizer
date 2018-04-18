package com.fed.omdbmemorizer.domain.model.mapper

import com.fed.omdbmemorizer.data.entity.MovieEntity
import com.fed.omdbmemorizer.domain.model.Movie


class MovieMapper : IMovieMapper {
    override fun fromEntityListToMovieList(listEntity: List<MovieEntity>?): List<Movie> {
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

    override fun fromMovieToEntity(movie: Movie): MovieEntity =
            MovieEntity(movie.title,
                    movie.year,
                    movie.poster)
}
package com.fed.omdbmemorizer.domain.model.mapper

import com.fed.omdbmemorizer.data.entity.MovieEntity
import com.fed.omdbmemorizer.domain.model.Movie


interface IMovieMapper {
    fun fromEntityListToMovieList(listEntity: List<MovieEntity>?): List<Movie>

    fun fromMovieToEntity(movie: Movie): MovieEntity
}
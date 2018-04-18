package com.fed.omdbmemorizer.presentation.model.mapper

import com.fed.omdbmemorizer.domain.model.Movie
import com.fed.omdbmemorizer.presentation.model.MovieView


interface IMovieViewMapper {
    fun fromMovieListToMovieViewList(listMovie: List<Movie>?): List<MovieView>

    fun fromMovieViewToMovie(movieView: MovieView): Movie
}
package com.fed.omdbmemorizer.presentation.model.mapper

import com.fed.omdbmemorizer.domain.model.Movie
import com.fed.omdbmemorizer.presentation.model.MovieView


class MovieViewMapper : IMovieViewMapper {
    override fun fromMovieListToMovieViewList(listMovie: List<Movie>?): List<MovieView> {
        val listMovieView = ArrayList<MovieView>()
        if (listMovie != null) {
            for (movie in listMovie) {
                listMovieView.add(MovieView(
                        movie.title,
                        movie.year,
                        movie.poster
                ))
            }
        }
        return listMovieView
    }

    override fun fromMovieViewToMovie(movieView: MovieView): Movie =
            Movie(movieView.title,
                    movieView.year,
                    movieView.poster)
}
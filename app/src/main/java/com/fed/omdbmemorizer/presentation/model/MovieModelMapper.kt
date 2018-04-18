package com.fed.omdbmemorizer.presentation.model

import com.fed.omdbmemorizer.domain.model.Movie


class MovieModelMapper {
    fun fromMovieListToModelList(listMovie: List<Movie>?): List<MovieModel> {
        val listMovieModel = ArrayList<MovieModel>()
        if (listMovie != null) {
            for (movie in listMovie) {
                listMovieModel.add(MovieModel(
                        movie.title,
                        movie.year,
                        movie.poster
                ))
            }
        }
        return listMovieModel
    }

    fun fromModeltoMovie(movieModel: MovieModel): Movie =
            Movie(movieModel.title,
                    movieModel.year,
                    movieModel.poster)
}
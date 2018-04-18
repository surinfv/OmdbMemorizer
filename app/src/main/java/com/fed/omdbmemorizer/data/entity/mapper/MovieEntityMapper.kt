package com.fed.omdbmemorizer.data.entity.mapper

import com.fed.omdbmemorizer.data.entity.MovieDTO
import com.fed.omdbmemorizer.data.entity.MovieEntity


class MovieEntityMapper : IMovieEntityMapper {
    override fun fromDTOListToEntityList(listDTO: List<MovieDTO>?): List<MovieEntity> {
        val listMovieEntity = ArrayList<MovieEntity>()
        if (listDTO != null) {
            for (movieDTO in listDTO) {
                listMovieEntity.add(MovieEntity(movieDTO.title,
                        movieDTO.year,
                        movieDTO.poster
                ))
            }
        }
        return listMovieEntity
    }
}
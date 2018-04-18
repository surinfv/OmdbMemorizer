package com.fed.omdbmemorizer.data.entity.mapper

import com.fed.omdbmemorizer.data.entity.MovieDTO
import com.fed.omdbmemorizer.data.entity.MovieEntity


interface IMovieEntityMapper {
    fun fromDTOListToEntityList(listDTO: List<MovieDTO>?): List<MovieEntity>
}
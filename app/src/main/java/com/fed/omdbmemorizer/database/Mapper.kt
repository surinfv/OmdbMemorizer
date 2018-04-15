package com.fed.omdbmemorizer.database

import com.fed.omdbmemorizer.model.MovieDTO
import com.fed.omdbmemorizer.model.MovieDbEntity
import com.fed.omdbmemorizer.model.MovieUiEntity


class Mapper {
    fun fromDTOtoUI(listDTO: ArrayList<MovieDTO>?): ArrayList<MovieUiEntity> {
        val listUI = ArrayList<MovieUiEntity>()
        if (listDTO != null) {
            for (movieDto in listDTO) {
                listUI.add(MovieUiEntity(
                        movieDto.title,
                        movieDto.year,
                        movieDto.poster
                ))
            }
        }
        return listUI
    }

    fun fromUItoDB(movieUI: MovieUiEntity): MovieDbEntity =
            MovieDbEntity(movieUI.title,
                          movieUI.year,
                          movieUI.poster)
}
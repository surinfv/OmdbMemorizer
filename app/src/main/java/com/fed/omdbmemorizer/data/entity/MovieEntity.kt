package com.fed.omdbmemorizer.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.fed.omdbmemorizer.data.entity.MovieEntity.Companion.TABLE_MOVIES_NAME

@Entity(tableName = TABLE_MOVIES_NAME)
data class MovieEntity(@PrimaryKey(autoGenerate = false)
                       var title: String = "",
                       var year: String = "",
                       var poster: String = "") {

    companion object {
        const val TABLE_MOVIES_NAME = "favoritesMovies"
    }
}
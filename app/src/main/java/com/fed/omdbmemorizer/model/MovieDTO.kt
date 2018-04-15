package com.fed.omdbmemorizer.model

import com.google.gson.annotations.SerializedName

data class MovieDTO(@SerializedName(FIELD_TYPE)
                    var type: String = "",
                    @SerializedName(FIELD_YEAR)
                    var year: String = "",
                    @SerializedName(FIELD_IMDB_ID)
                    var imdbID: String = "",
                    @SerializedName(FIELD_POSTER)
                    var poster: String = "",
                    @SerializedName(FIELD_TITLE)
                    var title: String = "") {

    companion object {
        const val FIELD_TYPE = "Type"
        const val FIELD_YEAR = "Year"
        const val FIELD_IMDB_ID = "imdbID"
        const val FIELD_POSTER = "Poster"
        const val FIELD_TITLE = "Title"
    }
}
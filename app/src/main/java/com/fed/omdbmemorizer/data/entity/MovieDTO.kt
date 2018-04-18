package com.fed.omdbmemorizer.data.entity

import com.google.gson.annotations.SerializedName

data class MovieDTO(@SerializedName(FIELD_TITLE)
                    var title: String = "",
                    @SerializedName(FIELD_YEAR)
                    var year: String = "",
                    @SerializedName(FIELD_POSTER)
                    var poster: String = "") {

    companion object {
        const val FIELD_YEAR = "Year"
        const val FIELD_POSTER = "Poster"
        const val FIELD_TITLE = "Title"
    }
}
package com.fed.omdbmemorizer.data.entity

import com.google.gson.annotations.SerializedName


data class ResponseDTO(@SerializedName(FIELD_RESPONSE)
                       val response: String = "",
                       @SerializedName(FIELD_TOTAL_RESULT)
                       val totalResults: String = "",
                       @SerializedName(FIELD_MOVIE_LIST)
                       val movieList: ArrayList<MovieDTO>?) {

    companion object {
        const val FIELD_RESPONSE = "Response"
        const val FIELD_TOTAL_RESULT = "totalResults"
        const val FIELD_MOVIE_LIST = "Search"
    }
}
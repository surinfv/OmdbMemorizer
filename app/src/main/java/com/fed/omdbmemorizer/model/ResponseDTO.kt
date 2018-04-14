package com.fed.omdbmemorizer.model

import com.google.gson.annotations.SerializedName

data class ResponseDTO(@SerializedName("Response")
                       val response: String = "",
                       @SerializedName("totalResults")
                       val totalResults: String = "",
                       @SerializedName("Search")
                       val movieList: ArrayList<MovieDTO>?)
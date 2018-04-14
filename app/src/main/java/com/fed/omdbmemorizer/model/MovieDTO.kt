package com.fed.omdbmemorizer.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favoritesMovies")
data class MovieDTO(@SerializedName("Type")
                    var type: String = "",
                    @SerializedName("Year")
                    var year: String = "",
                    @SerializedName("imdbID")
                    var imdbID: String = "",
                    @SerializedName("Poster")
                    var poster: String = "",
                    @SerializedName("Title")
                    @PrimaryKey(autoGenerate = false)
                    var title: String = "")
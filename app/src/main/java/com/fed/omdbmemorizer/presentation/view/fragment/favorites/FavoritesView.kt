package com.fed.omdbmemorizer.presentation.view.fragment.favorites

import com.fed.omdbmemorizer.presentation.model.MovieView


interface FavoritesView {
    fun showToast(message: String)
    fun updateData(movies: List<MovieView>)
}
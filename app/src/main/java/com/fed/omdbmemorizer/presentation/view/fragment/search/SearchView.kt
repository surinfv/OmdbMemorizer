package com.fed.omdbmemorizer.presentation.view.fragment.search

import com.fed.omdbmemorizer.presentation.model.MovieView


interface SearchView {
    fun updateData(movies: List<MovieView>)
    fun clearMoviesList()
    fun showToast(message: String)
    fun showProgress()
    fun hideProgress()
}
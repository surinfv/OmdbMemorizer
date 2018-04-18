package com.fed.omdbmemorizer.presentation.view.fragment.search

import com.fed.omdbmemorizer.presentation.model.MovieModel


interface SearchView {
    fun updateData(movies: List<MovieModel>)
    fun clearMoviesList()
    fun showToast(message: String)
    fun showProgress()
    fun hideProgress()
}
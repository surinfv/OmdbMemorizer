package com.fed.omdbmemorizer.presentation.view.presenter.favorites

import com.fed.omdbmemorizer.presentation.view.fragment.favorites.FavoritesView
import com.fed.omdbmemorizer.presentation.model.MovieModel


interface FavoritesPresenter {
    fun onResume(fragment: FavoritesView)
    fun onPause()
    fun removeFavoritesClick(movieModel: MovieModel)
}
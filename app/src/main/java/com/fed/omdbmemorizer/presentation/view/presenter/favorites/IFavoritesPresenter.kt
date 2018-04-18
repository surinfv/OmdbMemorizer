package com.fed.omdbmemorizer.presentation.view.presenter.favorites

import com.fed.omdbmemorizer.presentation.view.fragment.favorites.FavoritesView
import com.fed.omdbmemorizer.presentation.model.MovieView


interface IFavoritesPresenter {
    fun onResume(fragment: FavoritesView)
    fun onPause()
    fun removeFavoritesClick(movieVeiw: MovieView)
}
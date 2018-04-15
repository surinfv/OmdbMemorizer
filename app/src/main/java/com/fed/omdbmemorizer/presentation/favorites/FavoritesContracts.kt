package com.fed.omdbmemorizer.presentation.favorites

import com.fed.omdbmemorizer.model.MovieUiEntity


interface FavoritesContracts {
    interface Fragment {
        fun showToast(message: String)
        fun updateData(movies: List<MovieUiEntity>)
    }

    interface Presenter {
        fun onResume(fragment: FavoritesContracts.Fragment)
        fun onPause()
        fun removeFavoritesClick(movie: MovieUiEntity)
    }
}
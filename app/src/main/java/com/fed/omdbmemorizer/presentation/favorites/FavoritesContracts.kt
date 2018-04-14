package com.fed.omdbmemorizer.presentation.favorites

import com.fed.omdbmemorizer.model.MovieDTO


interface FavoritesContracts {
    interface Fragment {
        fun updateData(movies: List<MovieDTO>)
        fun showToast(message: String)
    }

    interface Presenter {
        fun onAttach(fragment: FavoritesContracts.Fragment)
        fun onDetach()
        fun loadFavorites()
    }
}
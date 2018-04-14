package com.fed.omdbmemorizer.presentation.favorites


interface FavoritesContracts {
    interface Fragment {
        fun showToast(message: String)
    }

    interface Presenter {
        fun onAttach(fragment: FavoritesContracts.Fragment)
        fun onDetach()
        fun loadFavorites()
    }
}
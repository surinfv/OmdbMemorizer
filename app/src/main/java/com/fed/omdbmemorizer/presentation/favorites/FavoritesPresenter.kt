package com.fed.omdbmemorizer.presentation.favorites

import com.fed.omdbmemorizer.repository.IRepository


class FavoritesPresenter(var repository: IRepository) : FavoritesContracts.Presenter {
    private var fragment: FavoritesContracts.Fragment? = null

    override fun onAttach(fragment: FavoritesContracts.Fragment) {
        this.fragment = fragment
    }

    override fun onDetach() {
        fragment = null
    }

    override fun loadFavorites() {

    }
}
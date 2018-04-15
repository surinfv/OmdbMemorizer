package com.fed.omdbmemorizer.presentation.favorites

import com.fed.omdbmemorizer.repository.IRepository
import io.reactivex.disposables.CompositeDisposable


class FavoritesPresenter(var repository: IRepository,
                         var disposable: CompositeDisposable) : FavoritesContracts.Presenter {
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
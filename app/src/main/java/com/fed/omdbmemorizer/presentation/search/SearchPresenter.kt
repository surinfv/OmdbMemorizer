package com.fed.omdbmemorizer.presentation.search

import com.fed.omdbmemorizer.repository.IRepository


class SearchPresenter(var repository: IRepository) : SearchContracts.Presenter {
    //    private var searchString = "batmen"
    private var fragment: SearchContracts.Fragment? = null

    override fun onAttach(fragment: SearchContracts.Fragment) {
        this.fragment = fragment
    }

    override fun onDetach() {
        fragment = null
    }

    override fun searchTextEntered(title: String) {
        fragment?.showProgress()
        repository.searchMovies(title)
                .subscribe({
                    if (it.movieList != null) {
                        fragment?.updateData(it.movieList)
                    } else {
                        fragment?.showToast("no movies with this title")
                    }
                    fragment?.hideProgress()
                }, Throwable::printStackTrace)
    }

    override fun clearButtonClicked() {
        fragment?.updateData(ArrayList())
    }
}
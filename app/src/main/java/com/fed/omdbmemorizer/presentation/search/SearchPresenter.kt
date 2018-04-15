package com.fed.omdbmemorizer.presentation.search

import android.util.Log
import com.fed.omdbmemorizer.model.MovieDTO
import com.fed.omdbmemorizer.repository.IRepository


class SearchPresenter(var repository: IRepository) : SearchContracts.Presenter {
    private val TAG = SearchPresenter::class.java.simpleName
    private var fragment: SearchContracts.Fragment? = null
    private var page: Int = 1
    private var lastQuery: String = ""
    private var loadingInProgress: Boolean = false

    override fun onAttach(fragment: SearchContracts.Fragment) {
        this.fragment = fragment
    }

    override fun onDetach() {
        fragment = null
    }

    override fun searchTextEntered(title: String) {
        lastQuery = title
        doRequest(true)
    }

    private fun doRequest(newRequest: Boolean) {
        if (!loadingInProgress) {
            loadingInProgress = true
            if (newRequest) fragment?.clearMoviesList()
            repository.searchMovies(lastQuery, page.toString())
                    .doOnSubscribe {
                        if (newRequest) fragment?.showProgress()
                    }
                    .doAfterTerminate {
                        fragment?.hideProgress()
                        loadingInProgress = false
                    }
                    .subscribe({
                        if (it.movieList != null) {
                            fragment?.updateData(it.movieList)
                        } else {
                            fragment?.showToast("no movies with this title")
                        }
                    }, {
                        fragment?.showToast("server error")
                        Log.e(TAG, "server error: " + it.printStackTrace())
                    })
        }
    }

    override fun clearButtonClicked() {
        fragment?.clearMoviesList()
        page = 1
        lastQuery = ""
        fragment?.hideProgress()
    }

    override fun lastItemsShown() {
        page++
        doRequest(false)
    }

    override fun addTofavorites(movie: MovieDTO) {
        repository.addFavorite(movie)
                .subscribe({
                    fragment?.showToast("${movie.title} in your favorites!")
                })

    }
}
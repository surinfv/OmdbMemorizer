package com.fed.omdbmemorizer.presentation.view.presenter.search

import com.fed.omdbmemorizer.presentation.model.MovieModel
import com.fed.omdbmemorizer.presentation.view.fragment.search.SearchView
import io.reactivex.Observable


interface SearchPresenter {
    fun onResume(fragment: SearchView)
    fun onPause()
    fun clearButtonClicked()
    fun lastItemsShown()
    fun addToFavorites(movie: MovieModel)
    fun onSetTextChangeListener(charSequence: Observable<CharSequence>)
}
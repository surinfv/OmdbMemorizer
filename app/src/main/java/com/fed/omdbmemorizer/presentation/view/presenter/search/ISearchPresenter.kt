package com.fed.omdbmemorizer.presentation.view.presenter.search

import com.fed.omdbmemorizer.presentation.model.MovieView
import com.fed.omdbmemorizer.presentation.view.fragment.search.SearchView
import io.reactivex.Observable


interface ISearchPresenter {
    fun onResume(fragment: SearchView)
    fun onPause()
    fun clearButtonClicked()
    fun lastItemsShown()
    fun addToFavorites(movie: MovieView)
    fun onSetTextChangeListener(charSequence: Observable<CharSequence>)
}
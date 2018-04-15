package com.fed.omdbmemorizer.presentation.search

import com.fed.omdbmemorizer.model.MovieUiEntity
import io.reactivex.Observable


interface SearchContracts {
    interface Fragment {
        fun updateData(movies: List<MovieUiEntity>)
        fun clearMoviesList()
        fun showToast(message: String)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun onResume(fragment: SearchContracts.Fragment)
        fun onPause()
        fun clearButtonClicked()
        fun lastItemsShown()
        fun addToFavorites(movie: MovieUiEntity)
        fun onSetTextChangeListener(charSequence: Observable<CharSequence>)
    }
}
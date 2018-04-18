package com.fed.omdbmemorizer.presentation.view.presenter.favorites

import android.util.Log
import com.fed.omdbmemorizer.domain.interactor.favoriteMovies.IFavoriteMoviesInteractor
import com.fed.omdbmemorizer.presentation.model.MovieView
import com.fed.omdbmemorizer.presentation.model.mapper.IMovieViewMapper
import com.fed.omdbmemorizer.presentation.view.fragment.favorites.FavoritesView
import io.reactivex.disposables.CompositeDisposable


class FavoritesPresenter(private var interactor: IFavoriteMoviesInteractor,
                         private var disposable: CompositeDisposable,
                         private var mapper: IMovieViewMapper) : IFavoritesPresenter {

    private val TAG = "FavoritesPresenter"
    private var fragment: FavoritesView? = null

    override fun onResume(fragment: FavoritesView) {
        this.fragment = fragment
        disposable.add(
                interactor.loadFavorites()
                        .subscribe({
                            fragment.updateData(mapper.fromMovieListToMovieViewList(it))
                        }, { Log.e(TAG, it.toString()) })
        )
    }

    override fun onPause() {
        fragment = null
        disposable.clear()
    }

    override fun removeFavoritesClick(movieView: MovieView) {
        interactor.removeFavorite(mapper.fromMovieViewToMovie(movieView))
                .subscribe({
                    fragment?.showToast("\"${movieView.title}\" - remove from favorites!")
                })
    }
}
package com.fed.omdbmemorizer.presentation.view.presenter.favorites

import android.util.Log
import com.fed.omdbmemorizer.domain.interactor.favoriteMovies.FavoriteMoviesInteractor
import com.fed.omdbmemorizer.presentation.view.fragment.favorites.FavoritesView
import com.fed.omdbmemorizer.presentation.model.MovieModel
import com.fed.omdbmemorizer.presentation.model.MovieModelMapper
import io.reactivex.disposables.CompositeDisposable


class FavoritesPresenterImpl(private var interactor: FavoriteMoviesInteractor,
                             private var disposable: CompositeDisposable,
                             private var mapper: MovieModelMapper) : FavoritesPresenter {

    private val TAG = "FavoritesPresenterImpl"
    private var fragment: FavoritesView? = null

    override fun onResume(fragment: FavoritesView) {
        this.fragment = fragment
        disposable.add(
                interactor.loadFavorites()
                        .subscribe({
                            fragment.updateData(mapper.fromMovieListToModelList(it))
                        }, { Log.e(TAG, it.toString()) })
        )
    }

    override fun onPause() {
        fragment = null
        disposable.clear()
    }

    override fun removeFavoritesClick(movieModel: MovieModel) {
        interactor.removeFavorite(mapper.fromModeltoMovie(movieModel))
                .subscribe({
                    fragment?.showToast("\"${movieModel.title}\" - remove from favorites!")
                })
    }
}
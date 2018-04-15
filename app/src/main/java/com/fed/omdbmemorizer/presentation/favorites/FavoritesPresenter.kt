package com.fed.omdbmemorizer.presentation.favorites

import android.util.Log
import com.fed.omdbmemorizer.model.MovieUiEntity
import com.fed.omdbmemorizer.repository.IRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class FavoritesPresenter(var repository: IRepository,
                         var disposable: CompositeDisposable) : FavoritesContracts.Presenter {
    private val TAG = "FavoritesPresenter"

    private var fragment: FavoritesContracts.Fragment? = null

    override fun onResume(fragment: FavoritesContracts.Fragment) {
        this.fragment = fragment
        disposable.add(
                repository.loadFavorites()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            fragment.updateData(it)
                        }, { Log.e(TAG, it.toString()) })
        )
    }

    override fun onPause() {
        fragment = null
        disposable.clear()
    }

    override fun removeFavoritesClick(movie: MovieUiEntity) {
        repository.removeFavorite(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fragment?.showToast("\"${movie.title}\" - remove from favorites!")
                })
    }
}
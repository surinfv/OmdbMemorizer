package com.fed.omdbmemorizer.presentation.view.presenter.search

import android.util.Log
import com.fed.omdbmemorizer.presentation.model.MovieModel
import com.fed.omdbmemorizer.domain.interactor.favoriteMovies.FavoriteMoviesInteractor
import com.fed.omdbmemorizer.domain.interactor.searchMovies.SearchMoviesInteractor
import com.fed.omdbmemorizer.presentation.model.MovieModelMapper
import com.fed.omdbmemorizer.presentation.view.fragment.search.SearchView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class SearchPresenterImpl(private var searchInteractor: SearchMoviesInteractor,
                          private var favoriteInteractor: FavoriteMoviesInteractor,
                          private var disposable: CompositeDisposable,
                          private var mapper: MovieModelMapper) : SearchPresenter {
    private val TAG = "SearchPresenterImpl"

    private var fragment: SearchView? = null
    private var page = 1
    private var lastQuery = ""

    private val scrollListenerSubject = PublishSubject.create<String>()
    private lateinit var textChangeListenerObservable: Observable<String>
    private var emitByScroll = false
    private var isCalnceled = false

    override fun onResume(fragment: SearchView) {
        this.fragment = fragment
    }

    override fun onPause() {
        fragment = null
        disposable.clear()
    }

    override fun clearButtonClicked() {
        fragment?.hideProgress()
        prepareForNewQuery()
        isCalnceled = true
    }

    override fun addToFavorites(movie: MovieModel) {
        favoriteInteractor.addFavorite(mapper.fromModeltoMovie(movie))
                .subscribe({
                    fragment?.showToast("\"${movie.title}\" - in your favorites!")
                })
    }

    override fun lastItemsShown() {
        if (emitByScroll) {
            emitByScroll = false
            page++
            scrollListenerSubject.onNext(lastQuery)
        }
    }

    override fun onSetTextChangeListener(charSequence: Observable<CharSequence>) {
        textChangeListenerObservable = charSequence
                .debounce(1000, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .filter { it.length > 2 }
                .filter { it != lastQuery }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    prepareForNewQuery()
                    lastQuery = it.toString()
                    fragment?.showProgress()
                }

        subscribeToListeners()
    }

    private fun subscribeToListeners() {
        disposable.add(
            Observable.merge(textChangeListenerObservable, scrollListenerSubject)
                    .doOnNext { isCalnceled = false }
                    .observeOn(Schedulers.io())
                    .switchMap {
                        searchInteractor.searchMovies(lastQuery, page.toString()).toObservable()
                                .onErrorResumeNext(Observable.empty())
                    }
                    .filter { !isCalnceled }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { fragment?.hideProgress() }
                    .subscribe({
                        if (it.isNotEmpty()) {
                            fragment?.updateData(mapper.fromMovieListToModelList(it))
                            emitByScroll = true
                        } else {
                            fragment?.showToast("no more movies with this title")
                            emitByScroll = false
                        }
                    }, { Log.e(TAG, it.toString())})
        )
    }
    private fun prepareForNewQuery() {
        fragment?.clearMoviesList()
        page = 1
        lastQuery = ""
        emitByScroll = false
    }
}

package com.fed.omdbmemorizer.presentation.search

import android.util.Log
import com.fed.omdbmemorizer.model.MovieUiEntity
import com.fed.omdbmemorizer.repository.IRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class SearchPresenter(var repository: IRepository,
                      var disposable: CompositeDisposable) : SearchContracts.Presenter {
    private val TAG = "SearchPresenter"

    private var fragment: SearchContracts.Fragment? = null
    private var page = 1
    private var lastQuery = ""

    private val scrollListenerSubject = PublishSubject.create<String>()
    private lateinit var textChangeListenerObservable: Observable<String>
    private var emitByScroll = false

    override fun onResume(fragment: SearchContracts.Fragment) {
        this.fragment = fragment
    }

    override fun onPause() {
        fragment = null
        disposable.clear()
    }

    override fun clearButtonClicked() {
        fragment?.clearMoviesList()
        page = 1
        lastQuery = ""
        fragment?.hideProgress()
        emitByScroll = false
    }

    override fun addToFavorites(movie: MovieUiEntity) {
        repository.addFavorite(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                .debounce(500, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .filter { it.length > 2 }
                .filter { it != lastQuery }
                .doOnNext { lastQuery = it.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { fragment?.showProgress() }

        subscribeToListeners()
    }

    private fun subscribeToListeners() {
        disposable.add(
            Observable.merge(textChangeListenerObservable, scrollListenerSubject)
                    .observeOn(Schedulers.io())
                    .switchMap {
                        repository.searchMovies(lastQuery, page.toString()).toObservable()
                                .onErrorResumeNext(Observable.empty())
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { fragment?.hideProgress() }
                    .subscribe({
                        if (it.isNotEmpty()) {
                            fragment?.updateData(it)
                            emitByScroll = true
                        } else {
                            fragment?.showToast("no more movies with this title")
                            emitByScroll = false
                        }
                    }, { Log.e(TAG, it.toString())})
        )
    }
}

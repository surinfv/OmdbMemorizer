package com.fed.omdbmemorizer.presentation.search

import com.fed.omdbmemorizer.model.MovieDTO
import com.fed.omdbmemorizer.repository.IRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SearchPresenter(var repository: IRepository,
                      var disposable: CompositeDisposable) : SearchContracts.Presenter {
    private val TAG = "SearchPresenter"

    private var fragment: SearchContracts.Fragment? = null
    private var page = 1
    private var lastQuery = ""
//    private var noMoreMovies = true

    override fun onResume(fragment: SearchContracts.Fragment) {
        this.fragment = fragment
    }

    override fun onPause() {
        fragment = null
        disposable.dispose()
    }

    override fun clearButtonClicked() {
        fragment?.clearMoviesList()
        page = 1
        lastQuery = ""
        fragment?.hideProgress()
//        noMoreMovies = false
    }

    override fun lastItemsShown() {
        page++
        //TODO invoke new request if noMoreMovies == false
    }

//    override fun lastItemShownRx(lastItemShown: Observable<Any>): Observable<Any> {
//        page++
//        lastItemShown
//                .filter(noMoreMovies)
//    }

    override fun addTofavorites(movie: MovieDTO) {
        repository.addFavorite(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fragment?.showToast("\"${movie.title}\" - in your favorites!")
                })

    }

    override fun onSetTextChangeListener(charSequence: Observable<CharSequence>) {
        //TODO show progress somewhere
        charSequence
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter { it.length > 2 }
                .doOnNext {
//                    noMoreMovies = false
                    lastQuery = it.toString()
                }
                // TODO add emit from last item shown here
                .switchMap { repository.searchMovies(lastQuery, page.toString()).toObservable() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { fragment?.hideProgress() }
                .subscribe({
                    if (it.movieList != null) {
                        fragment?.updateData(it.movieList)
                    } else {
                        fragment?.showToast("no more movies with this title")
//                        noMoreMovies = true
                    }
                },{})
    }
}

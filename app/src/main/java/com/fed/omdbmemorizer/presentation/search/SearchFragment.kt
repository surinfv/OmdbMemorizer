package com.fed.omdbmemorizer.presentation.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.fed.omdbmemorizer.App
import com.fed.omdbmemorizer.R
import com.fed.omdbmemorizer.model.MovieDTO
import com.fed.omdbmemorizer.presentation.RecyclerAdapter
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.search_fragment_layout.nested_scroll
import kotlinx.android.synthetic.main.search_fragment_layout.progress_bar
import kotlinx.android.synthetic.main.search_fragment_layout.recycler_view
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchFragment : Fragment(), SearchContracts.Fragment {
    private lateinit var adapter: RecyclerAdapter
    @Inject
    lateinit var presenter: SearchContracts.Presenter
    private lateinit var disposable: CompositeDisposable

    override

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        App.component?.injects(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.isNestedScrollingEnabled = false
        adapter = RecyclerAdapter(context, ArrayList()) {movie -> addToFavoritesClick(movie)}
        recycler_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
        setListeners()
    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
        presenter.onDetach()
    }

    override fun updateData(movies: ArrayList<MovieDTO>) {
        adapter.addMovies(movies)
        val now = adapter.itemCount
        val before = now - movies.size
        for (i in before + 1..now) {
            adapter.notifyItemInserted(i)
        }
    }

    override fun clearMoviesList() {
        adapter.clearMovies()
        adapter.notifyDataSetChanged()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun setListeners() {
        val searchTextView = activity?.findViewById(R.id.search_text) as TextView
        val clearView = activity?.findViewById(R.id.clear_search_text) as View
        disposable = CompositeDisposable()
        disposable.addAll(
                RxTextView.textChanges(searchTextView)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .filter { it.length > 2 }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            presenter.searchTextEntered(it.toString())
                        },
                RxView.clicks(clearView)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            searchTextView.text = ""
                            presenter.clearButtonClicked()
                        }
        )

        //don't look at this
        val screensToEnd = 3
        nested_scroll.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                    if (scrollY > v.getChildAt(0).measuredHeight - v.measuredHeight * screensToEnd) {
                        presenter.lastItemsShown()
                    }
                })
    }

    override fun showProgress() {
        recycler_view.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        recycler_view.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    private fun addToFavoritesClick(movie: MovieDTO) {
        presenter.addTofavorites(movie)
    }
}
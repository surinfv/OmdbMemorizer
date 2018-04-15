package com.fed.omdbmemorizer.presentation.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fed.omdbmemorizer.R
import com.fed.omdbmemorizer.di.DiProvider
import com.fed.omdbmemorizer.model.MovieDTO
import com.fed.omdbmemorizer.presentation.RecyclerAdapter
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.search_fragment_layout.clear_text_view
import kotlinx.android.synthetic.main.search_fragment_layout.progress_bar
import kotlinx.android.synthetic.main.search_fragment_layout.recycler_view
import kotlinx.android.synthetic.main.search_fragment_layout.search_text_view
import javax.inject.Inject


class SearchFragment : Fragment(), SearchContracts.Fragment {
    private lateinit var adapter: RecyclerAdapter
    @Inject
    lateinit var presenter: SearchContracts.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        DiProvider.component?.injects(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.isNestedScrollingEnabled = false
        adapter = RecyclerAdapter(context, ArrayList()) { movie -> addToFavoritesClick(movie) }
        recycler_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume(this)
        setListeners()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun updateData(movies: ArrayList<MovieDTO>) {
        val before = adapter.itemCount
        adapter.addMovies(movies)
        val now = adapter.itemCount
        adapter.notifyItemRangeInserted(before + 1, now)
    }

    override fun clearMoviesList() {
        adapter.clearMovies()
        adapter.notifyDataSetChanged()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        recycler_view.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        recycler_view.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    private fun setListeners() {
        presenter.onSetTextChangeListener(RxTextView.textChanges(search_text_view))

        RxView.clicks(clear_text_view)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    search_text_view.setText("")
                    presenter.clearButtonClicked()
                }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition = (recyclerView?.layoutManager
                        as LinearLayoutManager).findLastVisibleItemPosition() + 1
                val totalItemCount = recyclerView.layoutManager.itemCount
                if (lastVisibleItemPosition > totalItemCount - 4) {
                    presenter.lastItemsShown()
                }
            }
        })
    }

    private fun addToFavoritesClick(movie: MovieDTO) {
        presenter.addToFavorites(movie)
    }
}
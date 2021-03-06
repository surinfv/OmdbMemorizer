package com.fed.omdbmemorizer.presentation.view.fragment.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fed.omdbmemorizer.R
import com.fed.omdbmemorizer.presentation.model.MovieView
import com.fed.omdbmemorizer.presentation.view.fragment.RecyclerAdapter
import com.fed.omdbmemorizer.presentation.view.presenter.favorites.IFavoritesPresenter
import kotlinx.android.synthetic.main.favorites_fragment_layout.placeholder_favorite
import kotlinx.android.synthetic.main.favorites_fragment_layout.recycler_view
import org.koin.android.ext.android.inject

class FavoritesFragment : Fragment(), FavoritesView {
    private lateinit var adapter: RecyclerAdapter
    private val presenter: IFavoritesPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favorites_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.isNestedScrollingEnabled = false
        adapter = RecyclerAdapter(context, ArrayList(), false) { movie -> removeFromFavorites(movie) }
        recycler_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume(this)
    }

    override fun updateData(movies: List<MovieView>) {
        adapter.setMovies(movies)
        adapter.notifyDataSetChanged()
        if (movies.isEmpty()) {
            recycler_view.visibility = View.GONE
            placeholder_favorite.visibility = View.VISIBLE
        } else {
            recycler_view.visibility = View.VISIBLE
            placeholder_favorite.visibility = View.GONE
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun removeFromFavorites(movie: MovieView) {
        presenter.removeFavoritesClick(movie)
    }
}
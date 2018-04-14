package com.fed.omdbmemorizer.ui.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fed.omdbmemorizer.App
import com.fed.omdbmemorizer.R
import com.fed.omdbmemorizer.model.MovieDTO
import com.fed.omdbmemorizer.ui.RecyclerAdapter
import kotlinx.android.synthetic.main.search_fragment_layout.recycler_view
import javax.inject.Inject


class SearchFragment : Fragment(), SearchContracts.Fragment {
    private var adapter: RecyclerAdapter? = null
    @Inject lateinit var presenter: SearchContracts.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
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
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
        presenter.doRequest("batman")
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    override fun updateData(movies: List<MovieDTO>) {
        if (adapter == null) adapter = RecyclerAdapter(context, movies)
        recycler_view.adapter = adapter
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
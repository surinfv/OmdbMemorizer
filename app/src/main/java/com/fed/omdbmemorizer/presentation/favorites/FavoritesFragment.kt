package com.fed.omdbmemorizer.presentation.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fed.omdbmemorizer.App
import com.fed.omdbmemorizer.R
import com.fed.omdbmemorizer.presentation.RecyclerAdapter
import kotlinx.android.synthetic.main.favorites_fragment_layout.recycler_view
import javax.inject.Inject


class FavoritesFragment : Fragment(), FavoritesContracts.Fragment {
    private var adapter: RecyclerAdapter? = null
    @Inject lateinit var presenter: FavoritesContracts.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        App.component?.injects(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favorites_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.isNestedScrollingEnabled = false
        adapter = RecyclerAdapter(context, ArrayList())
        recycler_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        presenter.loadFavorites()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
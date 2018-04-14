package com.fed.omdbmemorizer.ui.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fed.omdbmemorizer.R
import com.fed.omdbmemorizer.ui.RecyclerAdapter
import kotlinx.android.synthetic.main.favorites_fragment_layout.recycler_view


class FavoritesFragment : Fragment() {
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
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

        adapter = RecyclerAdapter(context, ArrayList())
        recycler_view.adapter = adapter
    }
}
package com.fed.imdbmemorizer.ui.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fed.imdbmemorizer.R
import com.fed.imdbmemorizer.ui.RecyclerAdapter
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
    }

    override fun onResume() {
        super.onResume()

        adapter = RecyclerAdapter(context, getPlaceHolders())
        recycler_view.adapter = adapter
    }

    private fun getPlaceHolders(): ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 1..50) {
            list.add("$i favorite item")
        }
        return list
    }
}
package com.fed.omdbmemorizer.ui

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.fed.omdbmemorizer.R
import com.fed.omdbmemorizer.ui.favorites.FavoritesFragment
import com.fed.omdbmemorizer.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.pager
import kotlinx.android.synthetic.main.activity_main.tabs
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupViewPager(pager)
        tabs.setupWithViewPager(pager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(SearchFragment(),
                applicationContext.getString(R.string.title_search))
        adapter.addFragment(FavoritesFragment(),
                applicationContext.getString(R.string.title_favorites))
        viewPager.adapter = adapter
    }
}

package com.fed.imdbmemorizer.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.fed.imdbmemorizer.R
import com.fed.imdbmemorizer.ui.favorites.FavoritesFragment
import com.fed.imdbmemorizer.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.navigation


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.navigation_search -> {
                fragment = SearchFragment()
            }
            R.id.navigation_favorites -> {
                fragment = FavoritesFragment()
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()

        true
    }
}

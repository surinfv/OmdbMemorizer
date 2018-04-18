package com.fed.omdbmemorizer.di

import com.fed.omdbmemorizer.presentation.view.fragment.favorites.FavoritesFragment
import com.fed.omdbmemorizer.presentation.view.fragment.search.SearchFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun injects(searchFragment: SearchFragment)
    fun injects(favoritesFragment: FavoritesFragment)
}
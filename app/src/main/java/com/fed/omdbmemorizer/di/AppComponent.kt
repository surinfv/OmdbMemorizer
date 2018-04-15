package com.fed.omdbmemorizer.di

import com.fed.omdbmemorizer.presentation.favorites.FavoritesFragment
import com.fed.omdbmemorizer.presentation.search.SearchFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun injects(searchFragment: SearchFragment)
    fun injects(favoritesFragment: FavoritesFragment)
}
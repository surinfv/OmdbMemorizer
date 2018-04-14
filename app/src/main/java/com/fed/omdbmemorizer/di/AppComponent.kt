package com.fed.omdbmemorizer.di

import com.fed.omdbmemorizer.ui.search.SearchFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun injects(searchFragment: SearchFragment)
}
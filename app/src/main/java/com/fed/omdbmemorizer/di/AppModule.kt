package com.fed.omdbmemorizer.di

import android.arch.persistence.room.Room
import android.content.Context
import com.fed.omdbmemorizer.database.AppDatabase
import com.fed.omdbmemorizer.database.MovieDAO
import com.fed.omdbmemorizer.network.OmdbApi
import com.fed.omdbmemorizer.presentation.favorites.FavoritesContracts
import com.fed.omdbmemorizer.presentation.favorites.FavoritesPresenter
import com.fed.omdbmemorizer.repository.IRepository
import com.fed.omdbmemorizer.repository.Repository
import com.fed.omdbmemorizer.presentation.search.SearchContracts
import com.fed.omdbmemorizer.presentation.search.SearchPresenter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideOmdbApi(): OmdbApi =
            Retrofit.Builder()
                    .baseUrl("http://www.omdbapi.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(OmdbApi::class.java)

    @Provides
    @Singleton
    fun provideMovieDao(): MovieDAO =
            Room.databaseBuilder(context, AppDatabase::class.java, "favoritesMovies-db")
                .build()
                .movieDAO

    @Provides
    @Singleton
    fun provideRepository(omdbApi: OmdbApi, movieDAO: MovieDAO)
            : IRepository = Repository(omdbApi, movieDAO)

    @Provides
    @Singleton
    fun provideSearchPresenter(repository: IRepository)
            : SearchContracts.Presenter = SearchPresenter(repository)

    @Provides
    @Singleton
    fun provideFavoritePresenter(repository: IRepository)
            : FavoritesContracts.Presenter = FavoritesPresenter(repository)
}
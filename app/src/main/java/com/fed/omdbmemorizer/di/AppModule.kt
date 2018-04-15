package com.fed.omdbmemorizer.di

import android.arch.persistence.room.Room
import android.content.Context
import com.fed.omdbmemorizer.database.AppDatabase
import com.fed.omdbmemorizer.database.MovieDAO
import com.fed.omdbmemorizer.network.OmdbApi
import com.fed.omdbmemorizer.presentation.favorites.FavoritesContracts
import com.fed.omdbmemorizer.presentation.favorites.FavoritesPresenter
import com.fed.omdbmemorizer.presentation.search.SearchContracts
import com.fed.omdbmemorizer.presentation.search.SearchPresenter
import com.fed.omdbmemorizer.repository.IRepository
import com.fed.omdbmemorizer.repository.Repository
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule(private val context: Context) {

    private val TABLE_NAME = "favoritesMovies-db"
    private val BASE_URL = "http://www.omdbapi.com"

    @Provides
    @Singleton
    fun provideOmdbApi(): OmdbApi =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(OmdbApi::class.java)

    @Provides
    @Singleton
    fun provideMovieDao(): MovieDAO =
            Room.databaseBuilder(context, AppDatabase::class.java, TABLE_NAME)
                    .build()
                    .movieDAO

    @Provides
    @Singleton
    fun provideRepository(omdbApi: OmdbApi, movieDAO: MovieDAO)
            : IRepository = Repository(omdbApi, movieDAO)

    @Provides
    @Singleton
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    @Singleton
    fun provideSearchPresenter(repository: IRepository, disposable: CompositeDisposable)
            : SearchContracts.Presenter = SearchPresenter(repository, disposable)

    @Provides
    @Singleton
    fun provideFavoritePresenter(repository: IRepository, disposable: CompositeDisposable)
            : FavoritesContracts.Presenter = FavoritesPresenter(repository, disposable)
}
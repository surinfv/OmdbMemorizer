package com.fed.omdbmemorizer.di

import android.arch.persistence.room.Room
import android.content.Context
import com.fed.omdbmemorizer.data.database.AppDatabase
import com.fed.omdbmemorizer.data.database.MovieDAO
import com.fed.omdbmemorizer.data.network.OmdbApi
import com.fed.omdbmemorizer.data.repository.LocalMovieStore
import com.fed.omdbmemorizer.data.repository.MovieRepository
import com.fed.omdbmemorizer.data.repository.MovieRepositoryInterface
import com.fed.omdbmemorizer.data.repository.NetworkMovieStore
import com.fed.omdbmemorizer.domain.interactor.favoriteMovies.FavoriteMoviesImpl
import com.fed.omdbmemorizer.domain.interactor.favoriteMovies.FavoriteMoviesInteractor
import com.fed.omdbmemorizer.domain.interactor.searchMovies.SearchMoviesImpl
import com.fed.omdbmemorizer.domain.interactor.searchMovies.SearchMoviesInteractor
import com.fed.omdbmemorizer.presentation.view.presenter.favorites.FavoritesPresenter
import com.fed.omdbmemorizer.presentation.view.presenter.favorites.FavoritesPresenterImpl
import com.fed.omdbmemorizer.presentation.model.MovieModelMapper
import com.fed.omdbmemorizer.presentation.view.presenter.search.SearchPresenter
import com.fed.omdbmemorizer.presentation.view.presenter.search.SearchPresenterImpl
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
    fun provideLocalMovieStore(movieDao: MovieDAO): LocalMovieStore =
            LocalMovieStore(movieDao)

    @Provides
    @Singleton
    fun provideNetworkMovieStore(api: OmdbApi): NetworkMovieStore =
            NetworkMovieStore(api)

    @Provides
    @Singleton
    fun provideRepository(localMovieStore: LocalMovieStore,
                          networkMovieStore: NetworkMovieStore): MovieRepositoryInterface = MovieRepository(localMovieStore, networkMovieStore)

    @Provides
    @Singleton
    fun provideSearchInteractor(repository: MovieRepositoryInterface) : SearchMoviesInteractor =
            SearchMoviesImpl(repository)

    @Provides
    @Singleton
    fun provideFavoritesInteractor(repository: MovieRepositoryInterface) : FavoriteMoviesInteractor =
            FavoriteMoviesImpl(repository)


    @Provides
    @Singleton
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun provideMovieModelMapper(): MovieModelMapper = MovieModelMapper()

    @Provides
    @Singleton
    fun provideSearchPresenter(searchInteractor: SearchMoviesInteractor,
                               favoriteInteractor: FavoriteMoviesInteractor,
                               disposable: CompositeDisposable,
                               mapper: MovieModelMapper) : SearchPresenter =
            SearchPresenterImpl(searchInteractor,
                    favoriteInteractor,
                    disposable,
                    mapper)

    @Provides
    @Singleton
    fun provideFavoritePresenter(interactor: FavoriteMoviesInteractor,
                                 disposable: CompositeDisposable,
                                 mapper: MovieModelMapper)
            : FavoritesPresenter = FavoritesPresenterImpl(interactor, disposable, mapper)
}
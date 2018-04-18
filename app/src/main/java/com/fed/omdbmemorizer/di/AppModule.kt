package com.fed.omdbmemorizer.di

import android.arch.persistence.room.Room
import android.content.Context
import com.fed.omdbmemorizer.data.database.AppDatabase
import com.fed.omdbmemorizer.data.database.MovieDAO
import com.fed.omdbmemorizer.data.entity.mapper.IMovieEntityMapper
import com.fed.omdbmemorizer.data.entity.mapper.MovieEntityMapper
import com.fed.omdbmemorizer.data.network.OmdbApi
import com.fed.omdbmemorizer.data.repository.IMovieRepository
import com.fed.omdbmemorizer.data.repository.MovieRepository
import com.fed.omdbmemorizer.data.repository.local.ILocalMovieStore
import com.fed.omdbmemorizer.data.repository.local.LocalMovieStore
import com.fed.omdbmemorizer.data.repository.net.INetworkMovieStore
import com.fed.omdbmemorizer.data.repository.net.NetworkMovieStore
import com.fed.omdbmemorizer.domain.interactor.favoriteMovies.FavoriteMoviesInteractor
import com.fed.omdbmemorizer.domain.interactor.favoriteMovies.IFavoriteMoviesInteractor
import com.fed.omdbmemorizer.domain.interactor.searchMovies.ISearchMoviesInteractor
import com.fed.omdbmemorizer.domain.interactor.searchMovies.SearchMoviesInteractor
import com.fed.omdbmemorizer.presentation.model.mapper.IMovieViewMapper
import com.fed.omdbmemorizer.presentation.model.mapper.MovieViewMapper
import com.fed.omdbmemorizer.presentation.view.presenter.favorites.FavoritesPresenter
import com.fed.omdbmemorizer.presentation.view.presenter.favorites.IFavoritesPresenter
import com.fed.omdbmemorizer.presentation.view.presenter.search.ISearchPresenter
import com.fed.omdbmemorizer.presentation.view.presenter.search.SearchPresenter
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
    fun provideMovieEntityMapper(): IMovieEntityMapper = MovieEntityMapper()

    @Provides
    @Singleton
    fun provideLocalMovieStore(movieDao: MovieDAO): ILocalMovieStore =
            LocalMovieStore(movieDao)

    @Provides
    @Singleton
    fun provideNetworkMovieStore(api: OmdbApi, mapper: IMovieEntityMapper): INetworkMovieStore =
            NetworkMovieStore(api, mapper)

    @Provides
    @Singleton
    fun provideRepository(localMovieStore: ILocalMovieStore,
                          networkMovieStore: INetworkMovieStore): IMovieRepository = MovieRepository(localMovieStore, networkMovieStore)

    @Provides
    @Singleton
    fun provideSearchInteractor(repository: IMovieRepository): ISearchMoviesInteractor =
            SearchMoviesInteractor(repository)

    @Provides
    @Singleton
    fun provideFavoritesInteractor(repository: IMovieRepository): IFavoriteMoviesInteractor =
            FavoriteMoviesInteractor(repository)


    @Provides
    @Singleton
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun provideMovieViewMapper(): IMovieViewMapper = MovieViewMapper()

    @Provides
    @Singleton
    fun provideSearchPresenter(searchInteractor: ISearchMoviesInteractor,
                               favoriteInteractor: IFavoriteMoviesInteractor,
                               disposable: CompositeDisposable,
                               mapper: IMovieViewMapper): ISearchPresenter =
            SearchPresenter(searchInteractor,
                    favoriteInteractor,
                    disposable,
                    mapper)

    @Provides
    @Singleton
    fun provideFavoritePresenter(interactor: IFavoriteMoviesInteractor,
                                 disposable: CompositeDisposable,
                                 mapper: IMovieViewMapper)
            : IFavoritesPresenter = FavoritesPresenter(interactor, disposable, mapper)
}
package com.fed.omdbmemorizer

import android.app.Application
import android.arch.persistence.room.Room
import com.fed.omdbmemorizer.data.database.AppDatabase
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
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(koinModule))
    }

    private val TABLE_NAME = "favoritesMovies-db"
    private val BASE_URL = "http://www.omdbapi.com"

    val koinModule = module {
        single<IFavoritesPresenter> { create<FavoritesPresenter>() }
        single<IFavoriteMoviesInteractor> { create<FavoriteMoviesInteractor>() }
        single<IMovieRepository> { create<MovieRepository>() }
        single<ILocalMovieStore> { create<LocalMovieStore>() }
        single { Room.databaseBuilder(applicationContext, AppDatabase::class.java, TABLE_NAME)
                    .build()
                    .movieDAO
        }
        single<INetworkMovieStore> { create<NetworkMovieStore>() }
        single { Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(OmdbApi::class.java)
        }
        single<IMovieEntityMapper> { create<MovieEntityMapper>() }
        single { CompositeDisposable() }
        single<IMovieViewMapper> { create<MovieViewMapper>() }
        single<ISearchPresenter> { create<SearchPresenter>() }
        single<ISearchMoviesInteractor> { create<SearchMoviesInteractor>() }
    }
}
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
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    private val TABLE_NAME = "favoritesMovies-db"
    private val BASE_URL = "http://www.omdbapi.com"

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(koinModule)
        }
    }

    val koinModule = module {
        singleBy<IFavoritesPresenter, FavoritesPresenter>()
        singleBy<IFavoritesPresenter, FavoritesPresenter>()
        singleBy<IFavoriteMoviesInteractor, FavoriteMoviesInteractor>()
        singleBy<IMovieRepository, MovieRepository>()
        singleBy<ILocalMovieStore, LocalMovieStore>()
        single {
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, TABLE_NAME)
                    .build()
                    .movieDAO
        }
        singleBy<INetworkMovieStore, NetworkMovieStore>()
        single {
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(OmdbApi::class.java)
        }
        singleBy<IMovieEntityMapper, MovieEntityMapper>()
        single { CompositeDisposable() }
        singleBy<IMovieViewMapper, MovieViewMapper>()
        singleBy<ISearchPresenter, SearchPresenter>()
        singleBy<ISearchMoviesInteractor, SearchMoviesInteractor>()
    }
}
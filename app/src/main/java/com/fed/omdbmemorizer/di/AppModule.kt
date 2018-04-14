package com.fed.omdbmemorizer.di

import android.content.Context
import com.fed.omdbmemorizer.network.OmdbApi
import com.fed.omdbmemorizer.repository.IRepository
import com.fed.omdbmemorizer.repository.Repository
import com.fed.omdbmemorizer.ui.search.SearchContracts
import com.fed.omdbmemorizer.ui.search.SearchPresenter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule(context: Context) {

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
    fun provideRepository(omdbApi: OmdbApi): IRepository = Repository(omdbApi)

    @Provides
    @Singleton
    fun provideSearchPresenter(repository: IRepository)
            : SearchContracts.Presenter = SearchPresenter(repository)
}
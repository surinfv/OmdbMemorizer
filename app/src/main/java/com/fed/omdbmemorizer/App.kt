package com.fed.omdbmemorizer

import android.app.Application
import com.fed.omdbmemorizer.di.AppModule
import com.fed.omdbmemorizer.di.DaggerAppComponent
import com.fed.omdbmemorizer.di.DiProvider


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DiProvider.component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
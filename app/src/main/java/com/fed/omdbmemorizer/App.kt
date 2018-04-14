package com.fed.omdbmemorizer

import android.app.Application
import com.fed.omdbmemorizer.di.AppComponent
import com.fed.omdbmemorizer.di.AppModule
import com.fed.omdbmemorizer.di.DaggerAppComponent


class App : Application() {
    companion object {
        var component: AppComponent? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
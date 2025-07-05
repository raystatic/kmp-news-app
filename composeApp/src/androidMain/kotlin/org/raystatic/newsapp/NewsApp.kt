package org.raystatic.newsapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.raystatic.newsapp.di.initKoin


class NewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NewsApp)
            androidLogger(org.koin.core.logger.Level.ERROR)
        }
    }
}
package org.raystatic.newsapp.di

import org.koin.dsl.module
import org.raystatic.newsapp.data.datasource.remote.KtorNewsRemoteDataSource
import org.raystatic.newsapp.data.datasource.remote.NewsRemoteDataSource
import org.raystatic.newsapp.data.repository.NewsRepositoryImpl
import org.raystatic.newsapp.domain.repository.NewsRepository

val dataModule = module {
    // Remote Data Source - using KtorNewsRemoteDataSource as a singleton
    single<NewsRemoteDataSource> { KtorNewsRemoteDataSource() }

    // Repository - providing NewsRepositoryImpl as a singleton implementation of NewsRepository
    single<NewsRepository> { NewsRepositoryImpl(get()) }
    // Koin's get() will automatically resolve NewsRemoteDataSource from the definition above
}
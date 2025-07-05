package org.raystatic.newsapp.di

import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.raystatic.newsapp.presentation.NewsListViewModel

val presentationModule = module {
    factoryOf(::NewsListViewModel)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            dataModule,
            domainModule,
            presentationModule
        )
    }

// For iOS
fun initKoin() {
    initKoin {}
}
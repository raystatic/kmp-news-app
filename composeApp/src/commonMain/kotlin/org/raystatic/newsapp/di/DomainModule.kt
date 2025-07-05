package org.raystatic.newsapp.di

import org.koin.dsl.module
import org.raystatic.newsapp.domain.usecases.GetNewsUseCase

val domainModule = module {
    // factory means a new instance will be created each time it's requested
    factory { GetNewsUseCase(get()) }
}
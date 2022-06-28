package com.test.movieapp.di

import com.test.movieapp.data.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        UserRepository()
    }
}
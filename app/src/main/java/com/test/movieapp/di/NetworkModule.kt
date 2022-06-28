package com.test.movieapp.di

import com.oratakashi.viewbinding.core.tools.retrofit.createOkHttpClient
import com.oratakashi.viewbinding.core.tools.retrofit.createReactiveService
import com.test.movieapp.data.network.MyApi
import org.koin.dsl.module

val networkModule = module {
    single {
        createReactiveService(
            MyApi::class.java,
            createOkHttpClient(
                arrayOf(),
                null,
                null,
                true
            ),
            ""
        )
    }
}
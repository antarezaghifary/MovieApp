package com.test.movieapp.data.repository

import com.test.movieapp.data.model.genre.GenresItem
import com.test.movieapp.data.model.movie.ResultsItem
import com.test.movieapp.data.network.MyApi
import io.reactivex.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepository : KoinComponent {

    private val api: MyApi by inject()

    fun movie(
        page: Int,
        with_genres: Int
    ): Single<List<ResultsItem>> {
        return api.movie(
            page,
            10,
            "34d28168ca773abb8e7098976e940a85",
            with_genres,
            "en-US",
        ).map {
            it.results
        }
    }

    fun genre(): Single<List<GenresItem>> {
        return api.genre(
            "34d28168ca773abb8e7098976e940a85",
            "en-US"
        ).map {
            it.genres
        }
    }

}
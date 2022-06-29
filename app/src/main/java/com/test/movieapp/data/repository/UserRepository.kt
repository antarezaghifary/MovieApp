package com.test.movieapp.data.repository

import com.test.movieapp.data.model.genre.GenresItem
import com.test.movieapp.data.model.video.ThumbnailResponse
import com.test.movieapp.data.network.MyApi
import io.reactivex.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepository : KoinComponent {

    private val api: MyApi by inject()
    private val apiKey: String = "34d28168ca773abb8e7098976e940a85"
    private val language: String = "en-US"

    fun movie(
        page: Int,
        with_genres: Int
    ): Single<List<com.test.movieapp.data.model.movie.ResultsItem>> {
        return api.movie(
            page,
            10,
            apiKey,
            with_genres,
            language,
        ).map {
            it.results
        }
    }

    fun genre(): Single<List<GenresItem>> {
        return api.genre(
            apiKey,
            language
        ).map {
            it.genres
        }
    }

    fun review(
        page: Int,
        movie_id: Int
    ): Single<List<com.test.movieapp.data.model.review.ResultsItem>> {
        return api.review(
            movie_id,
            page,
            3,
            apiKey,
            language,
        ).map {
            it.results
        }
    }

    fun thumbnail(
        movie_id: Int
    ): Single<ThumbnailResponse> {
        return api.thumbnail(
            movie_id,
            "videos",
            apiKey
        )
    }

    fun trailer(
        movie_id: Int
    ): Single<List<com.test.movieapp.data.model.trailer.ResultsItem>> {
        return api.trailer(
            movie_id,
            apiKey
        ).map {
            it.results
        }
    }

}
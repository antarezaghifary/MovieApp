package com.test.movieapp.data.network

import com.test.movieapp.data.model.popular.GenreResponse
import com.test.movieapp.data.model.popular.PopularResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MyApi {
    @GET("discover/movie")
    fun popular(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("api_key") api_key: String,
        @Query("with_genres") with_genres: Int,
        @Header("language") language: String
    ): Single<PopularResponse>

    @GET("genre/movie/list")
    fun genre(
        @Query("api_key") api_key: String,
        @Header("language") language: String
    ): Single<GenreResponse>
}
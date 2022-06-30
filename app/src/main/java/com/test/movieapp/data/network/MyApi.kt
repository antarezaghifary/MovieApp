package com.test.movieapp.data.network

import com.test.movieapp.data.model.genre.GenreResponse
import com.test.movieapp.data.model.movie.MovieResponse
import com.test.movieapp.data.model.review.ReviewResponse
import com.test.movieapp.data.model.trailer.TrailerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApi {

    @GET("genre/movie/list")
    fun genre(
        @Query("api_key") api_key: String,
        @Header("language") language: String
    ): Single<GenreResponse>

    @GET("discover/movie")
    fun movie(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("api_key") api_key: String,
        @Query("with_genres") with_genres: Int,
        @Header("language") language: String
    ): Single<MovieResponse>

    @GET("movie/{movie_id}/reviews")
    fun review(
        @Path("movie_id") movie_id: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("api_key") api_key: String,
        @Header("language") language: String
    ): Single<ReviewResponse>

    @GET("movie/{movie_id}/videos")
    fun trailer(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): Single<TrailerResponse>
}
package com.dhani.mvvm.data.api

import com.dhani.mvvm.data.vo.MovieDetails
import com.dhani.mvvm.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Routes {


    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page : Int) : Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}
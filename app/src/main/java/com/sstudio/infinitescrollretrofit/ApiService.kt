package com.sstudio.infinitescrollretrofit

import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: String
    ): Call<Movies>
}
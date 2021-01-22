package com.sstudio.infinitescrollretrofit

import androidx.lifecycle.MutableLiveData

class MovieRepository(private val movieDataSource: MovieDataSource) {

    fun getMovies(page: Int): MutableLiveData<Movies> {
        return movieDataSource.getMovies(page)
    }
}
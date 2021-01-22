package com.sstudio.infinitescrollretrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val movieDataSource = MovieDataSource(ApiConfig.apiService)
    private val movieRepository = MovieRepository(movieDataSource)
    val page = MutableLiveData<Int>().also {
        it.value = 1
    }

    fun nextPage(){
        page.value = page.value?.plus(1)
    }
    var moviesPrevPage: ArrayList<Movies.Result> = ArrayList()
    var getPhoto: LiveData<Movies> = Transformations.switchMap(page) { mMovieTvId ->
        Transformations.map(movieRepository.getMovies(mMovieTvId)){
            val newMovies: ArrayList<Movies.Result> = ArrayList()
            newMovies.addAll(moviesPrevPage)
            newMovies.addAll(it.results)
            it.results = newMovies
            moviesPrevPage = newMovies
            it
        }
    }
}
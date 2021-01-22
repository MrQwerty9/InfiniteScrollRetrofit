package com.sstudio.infinitescrollretrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sstudio.pagingwithretrofit.ApiConfig

class MainViewModel : ViewModel() {
    private val photoDataSource = MovieDataSource(ApiConfig.apiService)
    private val photoRepository = MovieRepository(photoDataSource)
    val page = MutableLiveData<Int>().also {
        it.value = 1
    }

    fun nextPage(){
        page.value = page.value?.plus(1)
    }
    var moviesPrevPage: ArrayList<Movies.Result> = ArrayList()
    var getPhoto: LiveData<Movies> = Transformations.switchMap(page) { mMovieTvId ->
        val ab = photoRepository.getMovies(mMovieTvId)
        Transformations.map(ab){
            val arr: ArrayList<Movies.Result> = ArrayList()
            arr.addAll(moviesPrevPage)
            arr.addAll(it.results)
            it.results = arr
            moviesPrevPage = arr
            it
        }
    }

    var listMovies: LiveData<Movies>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = getPhoto
            }else{
                val list: ArrayList<Movies.Result> = ArrayList()
                listMovies?.value?.results?.let { list.addAll(it) }
                getPhoto.value?.results?.let { list.addAll(it) }
                listMovies?.value?.results = list
            }
            return field
        }
        private set
}
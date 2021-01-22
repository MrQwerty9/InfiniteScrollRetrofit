package com.sstudio.infinitescrollretrofit

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDataSource(private val apiService: ApiService) {

    fun getMovies(page: Int): MutableLiveData<Movies> {
        val result: MutableLiveData<Movies> = MutableLiveData()
        apiService.getPopularMovies(BuildConfig.TMDB_API_KEY, "en-US", page.toString())
            .enqueue(object : Callback<Movies> {
                override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                    result.value = response.body()
                }

                override fun onFailure(call: Call<Movies>, t: Throwable?) {}
            })
        return result
    }
}
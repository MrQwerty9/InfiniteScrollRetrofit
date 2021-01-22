package com.sstudio.infinitescrollretrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var isScroll = true
    private var lastPage = 0
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val layoutManager = GridLayoutManager(this, 3)
        movieAdapter= MovieAdapter()
        rv_photos.layoutManager = layoutManager
        rv_photos.adapter = movieAdapter

        mainViewModel.getPhoto.observe(this, { photos ->
            if (movieAdapter.dataPhoto.size > 0){
                isScroll = true
                movieAdapter.removeDataLoading()
            }
            lastPage = photos.total_pages!!
            movieAdapter.dataPhoto.clear()
            movieAdapter.dataPhoto.addAll(photos.results)
            movieAdapter.notifyDataSetChanged()
        })

        rv_photos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val countItems = layoutManager.itemCount
                val currentItem = layoutManager.childCount
                val firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                val totalScrollItem = currentItem + firstVisiblePosition

                if (isScroll && totalScrollItem >= countItems && mainViewModel.page.value!! <= lastPage){
                    mainViewModel.nextPage()
                    movieAdapter.addDataLoading()
                    isScroll = false
                }
            }
        })
    }
}
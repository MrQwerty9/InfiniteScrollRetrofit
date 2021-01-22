package com.sstudio.infinitescrollretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_photos.view.*


class MovieAdapter : RecyclerView.Adapter<MovieAdapter.PhotoViewHolder>() {

    var dataMovies: ArrayList<Movies.Result?> = ArrayList()
    private val TYPE_MOVIE = 1
    private val TYPE_LOADING = 2

    override fun getItemViewType(position: Int): Int {
        return if (dataMovies[position] != null) {
            TYPE_MOVIE
        } else {
            TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val view = if (i == TYPE_LOADING) {
            layoutInflater.inflate(R.layout.item_loading, viewGroup, false)
        } else {
            layoutInflater.inflate(R.layout.item_photos, viewGroup, false)
        }
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int = dataMovies.size

    override fun onBindViewHolder(photoViewHolder: PhotoViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_MOVIE) {
            dataMovies[position]?.let { photoViewHolder.bind(it) }
        }
    }

    fun setDataMovies(movies: List<Movies.Result>){
        dataMovies.clear()
        dataMovies.addAll(movies)
    }

    fun addDataLoading(){
        dataMovies.add(null)
        notifyDataSetChanged()
    }
    fun removeDataLoading(){
        dataMovies.removeAt(dataMovies.size - 1)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movies: Movies.Result) {
//            val url = GlideUrl(
//                photo.backdrop_path, LazyHeaders.Builder()
//                    .addHeader("User-Agent", "your-user-agent")
//                    .build()
//            )
            with(itemView) {
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + movies.poster_path)
                    .into(image_view)
            }
        }
    }
}
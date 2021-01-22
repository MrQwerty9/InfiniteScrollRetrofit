package com.sstudio.infinitescrollretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_photos.view.*


class MovieAdapter : RecyclerView.Adapter<MovieAdapter.PhotoViewHolder>() {

    var dataPhoto: ArrayList<Movies.Result?> = ArrayList()
    private val TYPE_PHOTO = 1
    private val TYPE_LOADING = 2

    override fun getItemViewType(position: Int): Int {
        return if (dataPhoto[position] != null) {
            TYPE_PHOTO
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

    override fun getItemCount(): Int = dataPhoto.size

    override fun onBindViewHolder(photoViewHolder: PhotoViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_PHOTO) {
            dataPhoto[position]?.let { photoViewHolder.bind(it) }
        }
    }

    fun addDataLoading(){
        dataPhoto.add(null)
        notifyDataSetChanged()
    }
    fun removeDataLoading(){
        dataPhoto.removeAt(dataPhoto.size - 1)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: Movies.Result) {
//            val url = GlideUrl(
//                photo.backdrop_path, LazyHeaders.Builder()
//                    .addHeader("User-Agent", "your-user-agent")
//                    .build()
//            )
            with(itemView) {
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_THUMBNAIL + photo.poster_path)
                    .into(image_view)
            }
        }
    }
}
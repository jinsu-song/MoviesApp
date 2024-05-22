package com.songjinsu.moviesapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.songjinsu.moviesapp.R
import com.songjinsu.moviesapp.common.App
import com.songjinsu.moviesapp.databinding.MovieListItemBinding
import com.songjinsu.moviesapp.datamodel.MovieInfo
import com.songjinsu.moviesapp.net.HttpRequest

class MovieListAdapter() : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private val list = arrayListOf<MovieInfo>()
    private var clickListener: (MovieInfo) -> Unit = {}
    lateinit var context: Context

    fun setList(items: ArrayList<MovieInfo>) {
        this.list.clear()
        this.list.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClick(listener : (MovieInfo) -> Unit) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)

        holder.binding.tvTitle.text = item.title
        holder.binding.tvVoteAverage.text = item.voteAverage.toString()
        holder.binding.tvReleaseDate.text = item.releaseDate

        holder.binding.linearlayout.setOnClickListener {
            clickListener(item)
        }

        if (context != null) {
            val url = "${App.configuration.images?.baseUrl}w92${item.posterPath}"
            if (url != null) {
                HttpRequest.imageLoad(
                    url,
                    Bitmap::class.java,
                    context,
                    { bitmap ->
                        if (bitmap != null) {
                            holder.binding.ivPoster.setImageBitmap(bitmap)
                        }
                    }, { error ->
                        Log.d("EEERRROOOOR", "Error Message >>>>>> : ${error.message}")
                    }
                )
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding by lazy {
            MovieListItemBinding.bind(itemView)
        }
    }
}
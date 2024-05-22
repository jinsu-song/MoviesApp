package com.songjinsu.moviesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.songjinsu.moviesapp.R
import com.songjinsu.moviesapp.databinding.MovieListItemBinding
import com.songjinsu.moviesapp.datamodel.MovieInfo

class MovieListAdapter() : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private val list = arrayListOf<MovieInfo>()
    private var clickListener: (MovieInfo) -> Unit = {}

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
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding by lazy {
            MovieListItemBinding.bind(itemView)
        }
    }
}
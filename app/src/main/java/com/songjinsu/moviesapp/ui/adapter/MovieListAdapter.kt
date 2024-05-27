package com.songjinsu.moviesapp.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.songjinsu.moviesapp.R
import com.songjinsu.moviesapp.MovieApplication
import com.songjinsu.moviesapp.databinding.MovieListItemBinding
import com.songjinsu.moviesapp.ui.datamodel.MovieInfo
import com.songjinsu.moviesapp.data.network.HttpRequest
import com.songjinsu.moviesapp.ui.MainViewModel

class MovieListAdapter(private val vm: MainViewModel) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private val list = arrayListOf<MovieInfo>()
    private var clickListener: (MovieInfo) -> Unit = {}
    lateinit var context: Context

    fun setList(items: ArrayList<MovieInfo>) {
        this.list.clear()
        this.list.addAll(items)
        notifyDataSetChanged()
    }

    // 기존의 리스트에 새로 추가될 때만 호출하는 메서드
    fun addList(items: ArrayList<MovieInfo>) {
        // 기존의 list에 새로 추가된 데이터의 시작 포지션
        val addStartPosition = list.size
        list.addAll(items)
        notifyItemRangeInserted(addStartPosition, items.size)
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


        if (this::context.isInitialized) {
            val baseUrl = vm.configuration.images?.baseUrl
            val url = "${baseUrl}w92${item.posterPath}"

            if (list[position].image != null) {
                holder.binding.ivPoster.setImageBitmap(list[position].image)
            } else {
                if (baseUrl != null) {
                    HttpRequest.imageLoad(
                        url,
                        Bitmap::class.java,
                        context,
                        { bitmap ->
                            if (bitmap != null) {
                                list[position].image = bitmap
                                holder.binding.ivPoster.setImageBitmap(bitmap)
                            }
                        }, { error ->
                            Log.d("EEERRROOOOR", "Error Message >>>>>> : ${error.message}")
                        }
                    )
                }
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
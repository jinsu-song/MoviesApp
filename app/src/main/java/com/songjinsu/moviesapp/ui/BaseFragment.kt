package com.songjinsu.moviesapp.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songjinsu.moviesapp.ui.adapter.MovieListAdapter
import com.songjinsu.moviesapp.ui.moviedetail.MovieDetailFragment

open class BaseFragment(val vm: MainViewModel) : Fragment() {


    protected fun setAdapter(context: Context, adapter: MovieListAdapter, recyclerView: RecyclerView, callback: () -> Unit) {

        adapter.context = context
        adapter.setOnItemClick { movieInfo ->
            vm.addFragment(MovieDetailFragment(vm, movieInfo.id.toString()))
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            private var isScrolledDown = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_SETTLING && isScrolledDown) {
                    callback()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isScrolledDown = dy > 0
            }
        })

    }
}
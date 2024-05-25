package com.songjinsu.moviesapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songjinsu.moviesapp.ui.MainViewModel
import com.songjinsu.moviesapp.ui.adapter.MovieListAdapter
import com.songjinsu.moviesapp.databinding.SearchFragmentBinding
import com.songjinsu.moviesapp.ui.datamodel.MovieInfo
import com.songjinsu.moviesapp.ui.datamodel.MovieSearch
import com.songjinsu.moviesapp.ui.moviedetail.MovieDetailFragment

class SearchFragment(val vm: MainViewModel) : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private var adapter: MovieListAdapter = MovieListAdapter()
    private var searchViewModel = SearchViewModel()
    private lateinit var searchInfo  : MovieSearch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    fun init() {
        adapter.context = requireContext()
        adapter.setOnItemClick { movieInfo ->
            vm.addFragment(MovieDetailFragment(vm, movieInfo.id.toString()))
        }
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter

        binding.recyclerview.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            private var isScrolledDown = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_SETTLING && isScrolledDown) {
                    val page = searchInfo.page?.plus(1)
                    val totalPage = searchInfo.totalPages
                    if (page != null && totalPage != null) {
                        if (page <= totalPage) {
                            searchViewModel.getSearchMovies(searchViewModel.movieName, requireContext(), page.toString())
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isScrolledDown = dy > 0
            }
        })

        searchViewModel.searchInfo.observe(viewLifecycleOwner) {
            searchInfo = it
            val movieList  = searchInfo.results as ArrayList<MovieInfo>
            var currentPage = searchInfo.page?.toInt()

            if (currentPage == 1) {
                adapter.setList(movieList)
            } else {
                adapter.addList(movieList)
            }
        }

        binding.btnSerach.setOnClickListener {
            val movieName : String = binding.etSearch.text.toString().trim()

            if (movieName.isNotEmpty()) {
                searchViewModel.getSearchMovies(movieName, requireContext())
                searchViewModel.movieName = movieName
            }
        }
    }
}
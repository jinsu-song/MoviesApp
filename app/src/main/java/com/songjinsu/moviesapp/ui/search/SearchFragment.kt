package com.songjinsu.moviesapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.songjinsu.moviesapp.ui.MainViewModel
import com.songjinsu.moviesapp.ui.adapter.MovieListAdapter
import com.songjinsu.moviesapp.databinding.SearchFragmentBinding
import com.songjinsu.moviesapp.ui.BaseFragment
import com.songjinsu.moviesapp.ui.datamodel.MovieInfo
import com.songjinsu.moviesapp.ui.datamodel.MovieSearch

class SearchFragment(vm: MainViewModel) : BaseFragment(vm) {

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

    private fun init() {
        setAdapter(requireContext(), adapter, binding.recyclerview) {
            val page = searchInfo.page?.plus(1)
            val totalPage = searchInfo.totalPages
            if (page != null && totalPage != null) {
                if (page <= totalPage) {
                    searchViewModel.getSearchMovies(searchViewModel.movieName, requireContext(), page.toString())
                }
            }
        }

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
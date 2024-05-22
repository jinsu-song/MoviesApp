package com.songjinsu.moviesapp.tab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.songjinsu.moviesapp.MainViewModel
import com.songjinsu.moviesapp.R
import com.songjinsu.moviesapp.adapter.MovieListAdapter
import com.songjinsu.moviesapp.databinding.SearchFragmentBinding
import com.songjinsu.moviesapp.datamodel.MovieInfo
import com.songjinsu.moviesapp.datamodel.MovieSearch
import com.songjinsu.moviesapp.movie_detail.MovieDetailFragment

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

        searchViewModel.searchInfo.observe(viewLifecycleOwner) {
            searchInfo = it
            val movieList = searchInfo.results as ArrayList<MovieInfo>
            adapter.setList(movieList)
        }

        binding.btnSerach.setOnClickListener {
            val movieName : String = binding.etSearch.text.toString().trim()

            if (movieName.isNotEmpty()) {
                searchViewModel.getSearchMovies(movieName, requireContext())
            }
        }
    }
}
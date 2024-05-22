package com.songjinsu.moviesapp.tab2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.songjinsu.moviesapp.MainViewModel
import com.songjinsu.moviesapp.adapter.MovieListAdapter
import com.songjinsu.moviesapp.databinding.HomeFragmentBinding
import com.songjinsu.moviesapp.datamodel.MovieInfo
import com.songjinsu.moviesapp.movie_detail.MovieDetailFragment

class HomeFragment(val vm: MainViewModel) : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private var  movieList : ArrayList<MovieInfo>? = arrayListOf()
    private var adapter : MovieListAdapter = MovieListAdapter()
    private val homeViewModel = HomeViewModel()
    private val filterViewFragment by lazy {
        FilterViewFragment(vm, homeViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

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

        // 영화 리스트 불러오기
        homeViewModel.getMovieList(requireContext())

        homeViewModel.movieListLiveData.observe(viewLifecycleOwner) {

            it.let { movieList = it }
            movieList?.let { it1 -> adapter.setList(it1) }

        }

        binding.btnFilter.setOnClickListener {
            vm.addFragment(filterViewFragment, slide = 2)
        }
    }
}
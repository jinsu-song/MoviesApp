package com.songjinsu.moviesapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songjinsu.moviesapp.ui.MainViewModel
import com.songjinsu.moviesapp.ui.adapter.MovieListAdapter
import com.songjinsu.moviesapp.databinding.HomeFragmentBinding
import com.songjinsu.moviesapp.ui.datamodel.MovieInfo
import com.songjinsu.moviesapp.ui.datamodel.MovieListResponse
import com.songjinsu.moviesapp.ui.datamodel.MovieListResponse1
import com.songjinsu.moviesapp.ui.datamodel.MovieListResponse2
import com.songjinsu.moviesapp.ui.moviedetail.MovieDetailFragment

class HomeFragment(val vm: MainViewModel) : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private var  movieList : ArrayList<MovieInfo>? = arrayListOf()
    private lateinit var moviesInfo : MovieListResponse
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

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var isScrolledDown = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_SETTLING && isScrolledDown) {
                    val page : String = (moviesInfo.page?.plus(1)).toString()
                    homeViewModel.getMovieList(requireContext(), homeViewModel.getMoviesFilterType(), page)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isScrolledDown = dy > 0;
            }
        })

        // 영화 리스트 불러오기
        homeViewModel.getMovieList(requireContext())

        homeViewModel.moviesInfoLiveData.observe(viewLifecycleOwner) {

            if (homeViewModel.isExistDateField()) {
                var moviesInfo = it as MovieListResponse1
                var currentPage = moviesInfo.page?.toInt()

                if (currentPage == 1) {
                    moviesInfo.results?.let { it1 -> adapter.setList(it1) }

                } else {
                    moviesInfo.results?.let { it1 -> adapter.addList(it1)}
                }

            } else {
                val moviesInfo = it as MovieListResponse2
                var currentPage = moviesInfo.page?.toInt()

                if (currentPage == 1) {
                    moviesInfo.results?.let { it1 -> adapter.setList(it1) }
                } else {
                    moviesInfo.results?.let { it1 -> adapter.addList(it1)}
                }
            }
            this.moviesInfo = it
        }

        binding.btnFilter.setOnClickListener {
            vm.addFragment(filterViewFragment, slide = 2)
        }
    }
}
package com.songjinsu.moviesapp.tab2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.songjinsu.moviesapp.MainViewModel
import com.songjinsu.moviesapp.R
import com.songjinsu.moviesapp.databinding.HomeFragmentBinding
import com.songjinsu.moviesapp.datamodel.MovieListResponse
import com.songjinsu.moviesapp.movie_detail.MovieDetailFragment

class HomeFragment(val vm: MainViewModel) : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private lateinit var  movieList : MovieListResponse
    private lateinit var movieDetailFragment : MovieDetailFragment


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
        vm.getMovieList(requireContext())

        vm.movieListLiveData.observe(viewLifecycleOwner) {
            Log.d("###@@##", "아 다행이다 왔다.")
        }

        binding.btnGoDetail.setOnClickListener {
            movieDetailFragment = MovieDetailFragment(vm)
            vm.addFragment(movieDetailFragment)
        }
    }
}
package com.songjinsu.moviesapp.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.songjinsu.moviesapp.MainViewModel
import com.songjinsu.moviesapp.databinding.MovieDetailFragmentBinding

class MovieDetailFragment(val vm: MainViewModel) : Fragment() {
    private lateinit var binding: MovieDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieDetailFragmentBinding.inflate(inflater, container, false)

        init()


        return binding.root
    }

    fun init() {
        binding.btnGoBack.setOnClickListener {
            vm.popFragment()
        }
    }
}
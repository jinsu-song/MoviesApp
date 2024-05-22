package com.songjinsu.moviesapp.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.songjinsu.moviesapp.MainViewModel
import com.songjinsu.moviesapp.databinding.MovieDetailFragmentBinding
import com.songjinsu.moviesapp.datamodel.MovieDetailResponse

class MovieDetailFragment(val vm: MainViewModel, val movieId: String) : Fragment() {
    private lateinit var binding: MovieDetailFragmentBinding
    private val movieDetailViewModel = MovieDetailViewModel()
    private lateinit var movieDetail : MovieDetailResponse

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

        movieDetailViewModel.getMovieDetail(movieId, requireContext())

        binding.btnGoBack.setOnClickListener {
            vm.popFragment()
        }

        movieDetailViewModel.movieDetailLiveData.observe(viewLifecycleOwner) {
            movieDetail = it

            binding.tvTitle.text = movieDetail.title
            binding.tvVoteAverage.text = movieDetail.voteAverage.toString()
            binding.tvOverview.text = movieDetail.overview

            // 이미지 불러오기
            movieDetail.posterPath?.let { it1 -> movieDetailViewModel.loadPoster(it1, requireContext()) }
        }

        movieDetailViewModel.posterLiveData.observe(viewLifecycleOwner) {
            binding.ivPoster.setImageBitmap(it)
        }
    }


}
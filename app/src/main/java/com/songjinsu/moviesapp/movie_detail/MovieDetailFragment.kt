package com.songjinsu.moviesapp.movie_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.songjinsu.moviesapp.MainViewModel
import com.songjinsu.moviesapp.common.App
import com.songjinsu.moviesapp.databinding.MovieDetailFragmentBinding
import com.songjinsu.moviesapp.datamodel.MovieDetailResponse
import com.songjinsu.moviesapp.datamodel.MovieVideo
import com.songjinsu.moviesapp.datamodel.MovieVideos

class MovieDetailFragment(val vm: MainViewModel, val movieId: String) : Fragment() {
    private lateinit var binding: MovieDetailFragmentBinding
    private val movieDetailViewModel = MovieDetailViewModel()
    private lateinit var movieDetail : MovieDetailResponse
    private lateinit var movieVideos: MovieVideos
    private lateinit var movieVideo: MovieVideo

    private val VIDEOS_TYPE : String = "Trailer"
    private val SITE : String = "YouTube"

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

        // 영화 상세 정보
        movieDetailViewModel.getMovieDetail(movieId, requireContext())

        // 영화 트레일러 정보
        movieDetailViewModel.getMovieVideos(movieId, requireContext())

        clickEvent()

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

        movieDetailViewModel.movieVideosLiveData.observe(viewLifecycleOwner) {
            movieVideos = it

            movieVideos.results?.forEach {

                if (it?.type == VIDEOS_TYPE && it.site == SITE) {
                    movieVideo = it
                    binding.goTrailer.visibility = View.VISIBLE
                }
            }
        }
    }

    fun clickEvent() {
        binding.btnGoBack.setOnClickListener {
            vm.popFragment()
        }

        binding.goTrailer.setOnClickListener {
            if (movieVideo != null) {
                App.openYoutube(movieId, requireContext())
            }
        }
    }
}
package com.songjinsu.moviesapp.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.songjinsu.moviesapp.ui.MainViewModel
import com.songjinsu.moviesapp.MovieApplication
import com.songjinsu.moviesapp.databinding.MovieDetailFragmentBinding
import com.songjinsu.moviesapp.ui.BaseFragment
import com.songjinsu.moviesapp.ui.datamodel.MovieDetailResponse
import com.songjinsu.moviesapp.ui.datamodel.MovieVideo
import com.songjinsu.moviesapp.ui.datamodel.MovieVideos

class MovieDetailFragment(vm: MainViewModel, val movieId: String) : BaseFragment(vm) {
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

        setObserver()
    }

    fun setObserver() {
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
                MovieApplication.openYoutube(movieId, requireContext())
            }
        }
    }
}
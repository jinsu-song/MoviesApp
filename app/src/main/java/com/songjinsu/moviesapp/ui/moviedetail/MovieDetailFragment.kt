package com.songjinsu.moviesapp.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.songjinsu.moviesapp.ui.MainViewModel
import com.songjinsu.moviesapp.R
import com.songjinsu.moviesapp.data.db.entities.FavoriteMovie
import com.songjinsu.moviesapp.databinding.MovieDetailFragmentBinding
import com.songjinsu.moviesapp.ui.BaseFragment
import com.songjinsu.moviesapp.ui.datamodel.MovieDetailResponse
import com.songjinsu.moviesapp.ui.datamodel.MovieVideo
import com.songjinsu.moviesapp.ui.datamodel.MovieVideos
import com.songjinsu.moviesapp.ui.favorite.FavoriteViewModel
import kotlinx.coroutines.launch

class MovieDetailFragment(vm: MainViewModel, private val movieId: String) : BaseFragment(vm) {
    private lateinit var binding: MovieDetailFragmentBinding
    private val movieDetailViewModel = MovieDetailViewModel(vm)
    var favoriteViewModel : FavoriteViewModel? = null
    private lateinit var movieDetail : MovieDetailResponse
    private lateinit var movieVideos: MovieVideos
    private lateinit var movieVideo: MovieVideo
    private var favoriteMovie: FavoriteMovie? = null
    private var isAddedFavoriteMovie : Boolean = false

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

    private fun init() {

        if (favoriteViewModel == null) {
            favoriteViewModel = FavoriteViewModel(requireContext())
        }

        // 영화 상세 정보
        movieDetailViewModel.getMovieDetail(movieId, requireContext())

        // 영화 트레일러 정보
        movieDetailViewModel.getMovieVideos(movieId, requireContext())

        // 즐겨찾기 추가 여부
        lifecycleScope.launch {
            favoriteViewModel?.isExistFavoriteMovie(movieId)
        }

        clickEvent()

        setObserver()
    }

    private fun setObserver() {
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

        favoriteViewModel?.movieLiveData?.observe(viewLifecycleOwner) { movie ->
            favoriteMovie = movie
            if (movie != null) {
                isAddedFavoriteMovie = true
                favoriteViewModel?.favoriteButtonClickLiveData?.postValue(true)
            } else {
                isAddedFavoriteMovie = false
                favoriteViewModel?.favoriteButtonClickLiveData?.postValue(false)
            }
        }

        favoriteViewModel?.favoriteButtonClickLiveData?.observe(viewLifecycleOwner) { isExistMovie ->
            if (isExistMovie) {
                binding.btnFavoriteMovie.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
                binding.btnFavoriteMovie.text = "즐겨찾기 해제"
            } else {
                binding.btnFavoriteMovie.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
                binding.btnFavoriteMovie.text = "즐겨찾기 추가"
            }
        }
    }

    private fun clickEvent() {
        binding.btnGoBack.setOnClickListener {
            vm.popFragment()
        }

        binding.goTrailer.setOnClickListener {
            if (this::movieVideo.isInitialized) {
                vm.openYouTube(movieId, requireContext())
            }
        }

        binding.btnFavoriteMovie.setOnClickListener {
            if (isAddedFavoriteMovie) {
                // 즐겨찾기 해제하기
                lifecycleScope.launch {
                    favoriteViewModel?.deleteFavoriteMovie(favoriteMovie!!)
                    isAddedFavoriteMovie = false
                }

            } else {
                // 즐겨찾기 추가하기
                lifecycleScope.launch {
                    favoriteViewModel?.insertFavoriteMovie(movieDetail.convertToFavoriteMovie(), requireContext())
                    isAddedFavoriteMovie = true
                }
            }
            lifecycleScope.launch {
                favoriteViewModel?.getFavoriteMovies()
            }

        }
    }
}
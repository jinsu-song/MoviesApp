package com.songjinsu.moviesapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.songjinsu.moviesapp.ui.MainViewModel
import com.songjinsu.moviesapp.common.MoviesFilterType
import com.songjinsu.moviesapp.databinding.FilterViewFragmentBinding
import com.songjinsu.moviesapp.ui.BaseFragment

class FilterViewFragment(vm : MainViewModel, val homeViewModel: HomeViewModel) : BaseFragment(vm) {
    private lateinit var binding: FilterViewFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterViewFragmentBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    private fun init() {

        // 인기순
        binding.btnFilterPopular.setOnClickListener {
            homeViewModel.getMovieList(requireContext())
            vm.popFragment()
        }

        // 평점수
        binding.btnFilterAverage.setOnClickListener {
            homeViewModel.getMovieList(requireContext(), MoviesFilterType.TOP_RATED)
            vm.popFragment()
        }

        // 신규순
        binding.btnFilterNew.setOnClickListener {
            homeViewModel.getMovieList(requireContext(), MoviesFilterType.UPCOMING)
            vm.popFragment()
        }

        // 즐겨찾기
        binding.btnFilterFavorite.setOnClickListener {
            Toast.makeText(requireContext(), "즐겨찾기에 대한 API가 없는것 같습니다.  ", Toast.LENGTH_SHORT).show()
            vm.popFragment()
        }

        binding.framelayout.setOnClickListener {
            vm.popFragment()
        }
    }
}
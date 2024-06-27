package com.songjinsu.moviesapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.songjinsu.moviesapp.databinding.FavoriteFragmentBinding
import com.songjinsu.moviesapp.ui.BaseFragment
import com.songjinsu.moviesapp.ui.MainViewModel
import com.songjinsu.moviesapp.ui.adapter.MovieListAdapter
import com.songjinsu.moviesapp.ui.datamodel.MovieInfo
import com.songjinsu.moviesapp.ui.moviedetail.MovieDetailFragment
import kotlinx.coroutines.launch

class FavoriteFragment(vm: MainViewModel) : BaseFragment(vm) {
    private lateinit var binding: FavoriteFragmentBinding
    private var adapter : MovieListAdapter = MovieListAdapter(vm)
    private val favoriteViewModel by lazy {
        FavoriteViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    private fun init() {
        adapter.context = requireContext()
        adapter.setOnItemClick { movieInfo ->
            val fragment = MovieDetailFragment(vm, movieInfo.id.toString())
            fragment.favoriteViewModel = favoriteViewModel
            vm.addFragment(fragment)
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter


        favoriteViewModel.getFavoriteMovies()


        setObserver()
    }

    private fun setObserver() {
        favoriteViewModel.favoriteMovieListLiveData.observe(viewLifecycleOwner) {
            val movieList = it

            if (movieList.size > 0) {
                val list = (movieList.map { it -> it.convertToMovieInfo()}) as ArrayList<MovieInfo>
                binding.recyclerview.visibility = View.VISIBLE
                binding.clEmptyMovies.visibility = View.GONE
                adapter.setList(list)
            } else {
                binding.recyclerview.visibility = View.GONE
                binding.clEmptyMovies.visibility = View.VISIBLE
            }
        }
    }
}
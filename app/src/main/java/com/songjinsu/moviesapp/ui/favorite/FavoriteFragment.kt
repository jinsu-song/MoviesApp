package com.songjinsu.moviesapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.songjinsu.moviesapp.databinding.FavoriteFragmentBinding
import com.songjinsu.moviesapp.ui.BaseFragment

class FavoriteFragment : BaseFragment() {
    private lateinit var binding: FavoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
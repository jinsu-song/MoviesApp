package com.songjinsu.moviesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.songjinsu.moviesapp.databinding.MainFragmentBinding
import com.songjinsu.moviesapp.datamodel.MovieListResponse
import com.songjinsu.moviesapp.tab1.SearchFragment
import com.songjinsu.moviesapp.tab2.HomeFragment
import com.songjinsu.moviesapp.tab3.FavoriteFragment

class MainFragment(val vm: MainViewModel) : Fragment() {

//    lateinit var  movieList : MovieListResponse
    private lateinit var binding : MainFragmentBinding

    val searchFragment = SearchFragment(vm)
    val homeFragment = HomeFragment(vm)
    val favoriteFragment = FavoriteFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)

        binding.tabLayout.getTabAt(1)?.select()
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.tab_container, homeFragment)?.commit()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return

                val position = tab.position
                var selected : Fragment? = null

                when(position) {
                    0 -> selected = searchFragment
                    1 -> selected = homeFragment
                    2 -> selected = favoriteFragment
                }

                if (selected == null) return

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.tab_container, selected)?.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
package com.songjinsu.moviesapp

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.VolleyError
import com.songjinsu.moviesapp.common.MoviesFilterType
import com.songjinsu.moviesapp.datamodel.MovieInfo
import com.songjinsu.moviesapp.datamodel.MovieListResponse
import com.songjinsu.moviesapp.datamodel.MovieListResponse1
import com.songjinsu.moviesapp.datamodel.MovieListResponse2
import com.songjinsu.moviesapp.datamodel.MovieSearch
import com.songjinsu.moviesapp.movie_detail.MovieDetailFragment
import com.songjinsu.moviesapp.net.API_PATHs
import com.songjinsu.moviesapp.net.HttpRequest
import com.songjinsu.moviesapp.net.Paths

class MainViewModel(val fm: FragmentManager) : ViewModel() {

    fun addFragment(fragment: Fragment, slide: Int = 1) {
        val tag = fragment::class.java.simpleName
        val ft = fm.beginTransaction()

        when(slide) {
            1 -> ft.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            2 -> ft.setCustomAnimations(
                R.anim.bottom_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.bottom_out
            )
        }


        ft.add(R.id.fragment_container_view, fragment)
        .addToBackStack(tag)
        .commit()
    }

    fun popFragment() {
        fm.popBackStack()
    }
}
package com.songjinsu.moviesapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.songjinsu.moviesapp.R

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
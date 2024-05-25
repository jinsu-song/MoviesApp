package com.songjinsu.moviesapp.ui

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.songjinsu.moviesapp.R
import com.songjinsu.moviesapp.common.App
import com.songjinsu.moviesapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel
    private lateinit var mainFragment : MainFragment

    val fm : FragmentManager = supportFragmentManager
    private val ft : FragmentTransaction = supportFragmentManager.beginTransaction()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = MainViewModel(fm)

        mainFragment = MainFragment(mainViewModel)

        App.getConfiguration(this) {
            ft.replace(R.id.fragment_container_view, mainFragment).commit()
        }

    }
}
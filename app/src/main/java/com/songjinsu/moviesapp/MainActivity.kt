package com.songjinsu.moviesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.android.volley.toolbox.Volley
import com.songjinsu.moviesapp.common.App
import com.songjinsu.moviesapp.databinding.ActivityMainBinding
import com.songjinsu.moviesapp.datamodel.MovieDetailResponse
import com.songjinsu.moviesapp.net.GsonRequest
//import com.songjinsu.moviesapp.net.NetApi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

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
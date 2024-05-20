package com.songjinsu.moviesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.Volley
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun getMovieDetailInfo() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.themoviedb.org/3/movie/637"

        val gsonRequest = GsonRequest(
            url,
            MovieDetailResponse::class.java,
            com.android.volley.Response.Listener { response ->
                // 응답 성공 시 처리
                println("Response >>> : $response")
            },
            com.android.volley.Response.ErrorListener { error ->
                // 오류 발생 시 처리
                error.printStackTrace()
            })

        queue.add(gsonRequest)
    }

    fun temp() {

        val task : HttpThread = HttpThread()
        task.start()
    }

    class HttpThread : Thread() {
        override fun run() {
            val client = OkHttpClient()

            val request = Request.Builder()
//                .url("https://api.themoviedb.org/3/movie/637")
//                .url("https://api.themoviedb.org/3/authentication")
                .url("https://api.themoviedb.org/3/genre/movie/list?language=en")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZWI2OWY5NDgxMzJmMzExNTBjYmIxNjllMWFlM2M3ZCIsInN1YiI6IjY2NDZiMTdjMWUwNjdiMzFjMzlhMmQyNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SzH3oNoMGtcSiRs_EZ-SlMu9GvKSYSzB1mV9sIXfeaY")
                .build()

            try {
                val response: Response = client.newCall(request).execute()

                response.body?.string()?.let { it1 -> Log.d("######", it1) }
            } catch (e: IOException) {
                Log.d("@@@@@@", "IOException 오류")
                e.printStackTrace()
            } catch (e: Exception) {
                Log.d("@@@@@@", "Exception 오류")
                e.printStackTrace()
            }
        }
    }

}
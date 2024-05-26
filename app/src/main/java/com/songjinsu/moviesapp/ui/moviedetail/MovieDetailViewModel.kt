package com.songjinsu.moviesapp.ui.moviedetail

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.songjinsu.moviesapp.common.App
import com.songjinsu.moviesapp.ui.datamodel.MovieDetailResponse
import com.songjinsu.moviesapp.ui.datamodel.MovieVideos
import com.songjinsu.moviesapp.net.HttpRequest
import com.songjinsu.moviesapp.net.Paths

class MovieDetailViewModel : ViewModel() {

    private val call = HttpRequest
    val movieDetailLiveData = MutableLiveData<MovieDetailResponse>()
    val posterLiveData = MutableLiveData<Bitmap>()
    val movieVideosLiveData = MutableLiveData<MovieVideos>()

    // 영화 정보 불러오기
    fun getMovieDetail(movieId: String, context: Context) {
        val methodName = "getMovieDetail"
        val url = Paths.makeFullUrl(Paths.movieDetail(movieId))

        call.requestGet(
            url,
            MovieDetailResponse::class.java,
            context,
            { response ->
                if (response == null) {
                    Toast.makeText(context, "Response is Null at $methodName", Toast.LENGTH_SHORT).show()
                } else {
                    movieDetailLiveData.postValue(response)
                }
            }, { error ->
                Toast.makeText(context, "$methodName >>>> ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // poster 이미지 불러오기
    fun loadPoster(imageName: String, context: Context) {
        val baseUrl = App.configuration.images?.baseUrl
        val url = "${baseUrl}w500${imageName}"
        if (baseUrl != null) {

            call.imageLoad(
                url,
                Bitmap::class.java,
                context,
                { bitmap ->
                    if (bitmap != null) {
                        posterLiveData.postValue(bitmap)
                    }

                }, { error ->
                    Log.d("Errorrrrr", "Error Message >>>>> : ${error.message}")
                }
            )
        }
    }

    // 영화에 대한 예고편 불러오기
    fun getMovieVideos(movieId: String, context: Context) {
        val methodName = "getMovieVideos"
        val baseUrl = App.configuration.images?.baseUrl
        val url = Paths.makeFullUrl(Paths.movieVideos(movieId))
        if (baseUrl != null) {
            call.requestGet(
                url,
                MovieVideos::class.java,
                context,
                { response ->
                    if (response == null) {
                        Toast.makeText(context, "Response is Null at $methodName", Toast.LENGTH_SHORT).show()
                    } else {
                        movieVideosLiveData.postValue(response)
                    }
                }, { error ->
                    Log.d("Errorrrrr", "Error Message >>>>> : ${error.message}")
                }
            )
        }
    }
}
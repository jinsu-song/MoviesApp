package com.songjinsu.moviesapp.movie_detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.songjinsu.moviesapp.datamodel.MovieDetailResponse
import com.songjinsu.moviesapp.net.HttpRequest
import com.songjinsu.moviesapp.net.Paths

class MovieDetailViewModel : ViewModel() {

    private val call = HttpRequest
    val movieDetailLiveData = MutableLiveData<MovieDetailResponse>()

    // 영화 정보 불러오기
    fun getMovieDetail(movieId: String, context: Context) {
        val methodName = "getMovieDetail"
        val url = Paths.makeFullUrl(Paths.MOVIE_DETAIL(movieId))

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
}
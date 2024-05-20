package com.songjinsu.moviesapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.songjinsu.moviesapp.common.MoviesFilterType
import com.songjinsu.moviesapp.datamodel.MovieListResponse
import com.songjinsu.moviesapp.datamodel.MovieListResponse1
import com.songjinsu.moviesapp.datamodel.MovieListResponse2
import com.songjinsu.moviesapp.net.API_PATHs
import com.songjinsu.moviesapp.net.HttpRequest

class MainViewModel : ViewModel() {

    private val call = HttpRequest
    private var moviesFilterType: MoviesFilterType = MoviesFilterType.POPULAR
    val liveData = MutableLiveData<MovieListResponse>()


    // 영화 목록 불러오기
    fun getMovieList(context: Context, moviesFilterType: MoviesFilterType = MoviesFilterType.POPULAR, page: String = "1") {
        val methodName = "getMovieList()"
        this.moviesFilterType = moviesFilterType
        val url = Paths.makeUrl(Paths.MOVIE_LIST(moviesFilterType.toString(), page))

        call.request(
            url,
            if (isExistDateField()) MovieListResponse1::class.java else MovieListResponse2::class.java,
            context,
            { response ->
                if (response == null) {
                    Toast.makeText(context, "Response is Null at $methodName", Toast.LENGTH_SHORT).show()
                } else {
                    liveData.postValue(response)
                }
            }, { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun getMoviesFilterType() : MoviesFilterType {
        return this.moviesFilterType
    }

    private fun isExistDateField() : Boolean {
        return when (moviesFilterType) {
            MoviesFilterType.POPULAR, MoviesFilterType.TOP_RATED -> false
            MoviesFilterType.NOW_PLAYING, MoviesFilterType.UPCOMING -> true
        }
    }
}

typealias Paths = API_PATHs
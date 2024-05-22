package com.songjinsu.moviesapp.tab2

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.songjinsu.moviesapp.common.MoviesFilterType
import com.songjinsu.moviesapp.datamodel.MovieInfo
import com.songjinsu.moviesapp.datamodel.MovieListResponse
import com.songjinsu.moviesapp.datamodel.MovieListResponse1
import com.songjinsu.moviesapp.datamodel.MovieListResponse2
import com.songjinsu.moviesapp.net.HttpRequest
import com.songjinsu.moviesapp.net.Paths

class HomeViewModel : ViewModel() {
    private val call = HttpRequest
    private var moviesFilterType: MoviesFilterType = MoviesFilterType.POPULAR
    val movieListLiveData = MutableLiveData<ArrayList<MovieInfo>?>()


    // 영화 목록 불러오기
    fun getMovieList(context: Context, moviesFilterType: MoviesFilterType = MoviesFilterType.POPULAR, page: String = "1") {
        val methodName = "getMovieList()"
        this.moviesFilterType = moviesFilterType
        val url = Paths.makeFullUrl(Paths.MOVIE_LIST(moviesFilterType.toString(), page))

        call.requestGet(
            url,
            if (isExistDateField()) MovieListResponse1::class.java else MovieListResponse2::class.java,
            context,
            { response ->
                if (response == null) {
                    Toast.makeText(context, "Response is Null at $methodName", Toast.LENGTH_SHORT).show()
                } else {
                    var res : MovieListResponse? = null

                    if (isExistDateField()) {
                        res = response as MovieListResponse1
                        movieListLiveData.postValue(res.results)

                    } else {
                        res = response as MovieListResponse2
                        movieListLiveData.postValue(res.results)
                    }
                }
            }, { error ->
                Toast.makeText(context, "$methodName >>>> ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun isExistDateField() : Boolean {
        return when (moviesFilterType) {
            MoviesFilterType.POPULAR, MoviesFilterType.TOP_RATED -> false
            MoviesFilterType.NOW_PLAYING, MoviesFilterType.UPCOMING -> true
        }
    }
}
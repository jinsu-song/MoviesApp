package com.songjinsu.moviesapp

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.songjinsu.moviesapp.common.MoviesFilterType
import com.songjinsu.moviesapp.datamodel.MovieListResponse
import com.songjinsu.moviesapp.datamodel.MovieListResponse1
import com.songjinsu.moviesapp.datamodel.MovieListResponse2
import com.songjinsu.moviesapp.datamodel.MovieSearch
import com.songjinsu.moviesapp.movie_detail.MovieDetailFragment
import com.songjinsu.moviesapp.net.API_PATHs
import com.songjinsu.moviesapp.net.HttpRequest
import com.songjinsu.moviesapp.net.Paths

class MainViewModel(val fm: FragmentManager) : ViewModel() {

    private val call = HttpRequest
    private var moviesFilterType: MoviesFilterType = MoviesFilterType.POPULAR
    val movieListLiveData = MutableLiveData<MovieListResponse>()
    val searchMovieLiveData = MutableLiveData<MovieSearch>()


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
                    movieListLiveData.postValue(response)
                }
            }, { error ->
                Toast.makeText(context, "$methodName >>>> ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // 영화 이름으로 검색
    fun searchMovie(context: Context, movieName: String, page: String = "1") {
        val methodName = "searchMovie"
        val url = Paths.makeFullUrl(Paths.SEARCH_MOVIE(movieName, page))

        call.requestGet(
            url,
            MovieSearch::class.java,
            context,
            { response ->
                // TODO :

            }, { error ->
                Toast.makeText(context, "$methodName >>>> ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun addFragment(fragment: Fragment) {
        val tag = fragment::class.java.simpleName
        val fg = fm.findFragmentByTag(tag)
        fm.beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .addToBackStack(tag)
            .commit()
    }

    fun popFragment() {
        fm.popBackStack()
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
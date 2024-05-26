package com.songjinsu.moviesapp.ui.search

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.songjinsu.moviesapp.ui.datamodel.MovieSearch
import com.songjinsu.moviesapp.network.HttpRequest
import com.songjinsu.moviesapp.network.Paths

class SearchViewModel : ViewModel() {
    private val call = HttpRequest
    val searchInfo = MutableLiveData<MovieSearch>()
    var movieName: String = ""

    fun getSearchMovies(movieName: String, context: Context, page: String = "1") {
        val methodName = "getSearchMovies"
        val url = Paths.makeFullUrl(Paths.searchMovie(movieName = movieName, page = page))

        call.requestGet(
            url,
            MovieSearch::class.java,
            context,
            { response ->
                if (response == null) {
                    Toast.makeText(context, "Response is Null at $methodName", Toast.LENGTH_SHORT).show()
                } else {
                    searchInfo.postValue(response)
                }
            }, { error ->
                Toast.makeText(context, "$methodName >>>> ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
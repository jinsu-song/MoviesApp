package com.songjinsu.moviesapp.net

object API_PATHs {
    val BASE_URL : String = "https://api.themoviedb.org/"

    val getMovieDetailInfo : String = "3/movie/{movieId}"

    val getGenre: String = "3/genre/movie/list?language=en"

    val getMovieIDs : String = "3/movie/changes?page=1"

    // Query the API configuration details.
    val CONFIGURATION : String = "3/configuration"

    fun MOVIE_LIST(type: String, page: String) : String = "3/movie/${type}?language=en-US&page=${page}"

    fun makeUrl(endpoint: String) : String{
        return "${BASE_URL}${endpoint}"
    }

}
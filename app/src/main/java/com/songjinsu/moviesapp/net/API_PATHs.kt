package com.songjinsu.moviesapp.net

object API_PATHs {
    val BASE_URL : String = "https://api.themoviedb.org/"

    val getMovieDetailInfo : String = "3/movie/{movieId}"

    val getGenre: String = "3/genre/movie/list?language=en"

    val getMovieIDs : String = "3/movie/changes?page=1"

}
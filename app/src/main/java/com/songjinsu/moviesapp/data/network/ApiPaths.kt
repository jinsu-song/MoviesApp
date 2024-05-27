package com.songjinsu.moviesapp.data.network

object ApiPaths {
    val BASE_URL : String = "https://api.themoviedb.org/"

    // Query the API configuration details.
    val CONFIGURATION : String = "3/configuration"

    /**
     * 영화 목록 불러오기
     * types
     * now_playing : 현재 상영작
     * popular : 인기
     * top_rated : 탑 랭킹 영화
     * upcoming : 상영예정 영화
     */
    fun movieList(type: String, page: String) : String = "3/movie/${type}?language=en-US&page=${page}"


    /**
     * 영화 이름으로 찾기
     */
    fun searchMovie(movieName: String, page: String) : String = "3/search/movie?query=${movieName}&include_adult=false&language=en-US&page=${page}"

    /**
     * 영화 상세 정보
     */
    fun movieDetail(movieId: String) : String = "3/movie/${movieId}?language=en-US"

    /**
     * 영화 트레일러 정보
     */
    fun movieVideos(movieId: String) : String = "3/movie/${movieId}/videos"

    fun makeFullUrl(endpoint: String) : String = "$BASE_URL${endpoint}"

}

typealias Paths = ApiPaths
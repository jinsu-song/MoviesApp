package com.songjinsu.moviesapp.datamodel


import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

open class MovieListResponse {
    @SerializedName("page")
    val page: Int? = null



    @SerializedName("total_pages")
    val totalPages: Int? = null
    @SerializedName("total_results")
    val totalResults: Int? = null
}

/**
 * For Type : now_playing, upcoming
 */
data class MovieListResponse1(
    @SerializedName("dates")
    val dates: Dates?,
//    @SerializedName("page")
//    val page: Int?,
    @SerializedName("results")
    val results: ArrayList<MovieInfo>?,
//    @SerializedName("total_pages")
//    val totalPages: Int?,
//    @SerializedName("total_results")
//    val totalResults: Int?
) : MovieListResponse()

/**
 * For Type : popular, Top Rated
 */
data class MovieListResponse2(
//    @SerializedName("page")
//    val page: Int?,
    @SerializedName("results")
    val results: ArrayList<MovieInfo>?,
//    @SerializedName("total_pages")
//    val totalPages: Int?,
//    @SerializedName("total_results")
//    val totalResults: Int?
) : MovieListResponse()

data class MovieSearch(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<MovieInfo?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

data class Dates(
    @SerializedName("maximum")
    val maximum: String?,
    @SerializedName("minimum")
    val minimum: String?
)

data class MovieInfo (
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int?>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,

    var image: Bitmap?
)
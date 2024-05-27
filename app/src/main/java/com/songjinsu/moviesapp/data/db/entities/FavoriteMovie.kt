package com.songjinsu.moviesapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.songjinsu.moviesapp.ui.datamodel.MovieInfo

@Entity(tableName = "favorite_movie_table")
data class FavoriteMovie(
    // 영화 ID
    @PrimaryKey val movieId: String,

    // 영화 제목
    @ColumnInfo(name = "title") val title: String,

    // 영화 평점
    @ColumnInfo(name = "vote_average") val voteAverage: Double,

    // 영화 개봉일
    @ColumnInfo(name = "release_date") val releaseDate: String,

    // 영화 포스터 Uri
    @ColumnInfo(name = "poster_path") val posterPath: String,

    // 튜플 생성일
    @ColumnInfo(name = "create_date") val createDate: String
) {
    fun convertToMovieInfo() : MovieInfo {
        return MovieInfo(
            id = movieId.toInt(),
            title = title,
            voteAverage = voteAverage,
            releaseDate = releaseDate,
            posterPath = posterPath,

            adult = null,
            backdropPath = null,
            genreIds = null,
            originalLanguage = null,
            originalTitle = null,
            overview = null,
            popularity = null,
            video = null,
            voteCount = null,
            image = null
        )
    }
}
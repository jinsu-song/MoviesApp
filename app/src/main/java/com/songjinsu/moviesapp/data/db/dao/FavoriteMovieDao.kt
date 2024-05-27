package com.songjinsu.moviesapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.songjinsu.moviesapp.data.db.entities.FavoriteMovie

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFavoriteMovie(movie: FavoriteMovie)

    @Query("DELETE FROM favorite_movie_table WHERE movieId = :movieId")
    suspend fun deleteFavoriteMovie(movieId: String) : Int

    @Query("SELECT * FROM favorite_movie_table")
    suspend fun getFavoriteMovies(): List<FavoriteMovie>

    @Query("SELECT * FROM favorite_movie_table WHERE movieId = :movieId")
    suspend fun isExistFavoriteMovie(movieId: String) : FavoriteMovie?

}
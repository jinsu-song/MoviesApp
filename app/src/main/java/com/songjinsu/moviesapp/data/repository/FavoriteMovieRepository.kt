package com.songjinsu.moviesapp.data.repository

import com.songjinsu.moviesapp.data.db.dao.FavoriteMovieDao
import com.songjinsu.moviesapp.data.db.entities.FavoriteMovie

class FavoriteMovieRepository(private val favoriteMovieDao: FavoriteMovieDao) {

    suspend fun insert(movie: FavoriteMovie) = favoriteMovieDao.insertFavoriteMovie(movie)

    suspend fun delete(movieId: String) = favoriteMovieDao.deleteFavoriteMovie(movieId)

    suspend fun getFavoriteMovies() : List<FavoriteMovie> = favoriteMovieDao.getFavoriteMovies()

    suspend fun isExistFavoriteMovie(movieId: String) = favoriteMovieDao.isExistFavoriteMovie(movieId)
}
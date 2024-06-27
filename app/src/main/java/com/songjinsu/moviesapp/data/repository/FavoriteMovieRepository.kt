package com.songjinsu.moviesapp.data.repository

import com.songjinsu.moviesapp.data.db.dao.FavoriteMovieDao
import com.songjinsu.moviesapp.data.db.entities.FavoriteMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteMovieRepository(private val favoriteMovieDao: FavoriteMovieDao, private val dispatchers: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun insert(movie: FavoriteMovie) = withContext(dispatchers) { favoriteMovieDao.insertFavoriteMovie(movie) }

    suspend fun delete(movieId: String) = withContext(dispatchers) { favoriteMovieDao.deleteFavoriteMovie(movieId) }

    suspend fun getFavoriteMovies() : List<FavoriteMovie> = withContext(dispatchers) { favoriteMovieDao.getFavoriteMovies() }

    suspend fun isExistFavoriteMovie(movieId: String) = withContext(dispatchers) { favoriteMovieDao.isExistFavoriteMovie(movieId) }
}
package com.songjinsu.moviesapp.ui.favorite

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.songjinsu.moviesapp.data.db.AppDatabase
import com.songjinsu.moviesapp.data.db.entities.FavoriteMovie
import com.songjinsu.moviesapp.data.repository.FavoriteMovieRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(context: Context) : ViewModel() {
    private val favoriteMovieRepository: FavoriteMovieRepository

    // 즐겨찾기에 추가 되어 있는지에 대한 LiveData
    val movieLiveData = MutableLiveData<FavoriteMovie?>()

    // 즐겨찾기 목록
    val favoriteMovieListLiveData = MutableLiveData<ArrayList<FavoriteMovie>>()

    val favoriteButtonClickLiveData = MutableLiveData<Boolean>()

    init {
        val dao = AppDatabase.getInstance(context).favoriteMovieDao()
        favoriteMovieRepository = FavoriteMovieRepository(dao)
    }

    suspend fun insertFavoriteMovie(movie: FavoriteMovie, context: Context) {
        try {
            favoriteMovieRepository.insert(movie)

            favoriteButtonClickLiveData.postValue(true)

        } catch (e: SQLiteConstraintException) {
            Toast.makeText(context, "이미 존재하는 영화 입니다.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "삽입에 실패했습니다. ", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteFavoriteMovie(movie: FavoriteMovie)  {
        viewModelScope.launch {
            val deleteCount : Int = favoriteMovieRepository.delete(movie.movieId)
            if (deleteCount > 0) {
                favoriteButtonClickLiveData.postValue(false)
            }
        }
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            val movieList = favoriteMovieRepository.getFavoriteMovies()

            if (movieList.size > 0) {
                favoriteMovieListLiveData.postValue(movieList as ArrayList<FavoriteMovie>?)
            } else {
                favoriteMovieListLiveData.postValue(ArrayList<FavoriteMovie>())
            }
        }
    }

    fun isExistFavoriteMovie(movieId : String) {
        viewModelScope.launch {
            val movie = favoriteMovieRepository.isExistFavoriteMovie(movieId)
            movieLiveData.postValue(movie)
        }
    }
}
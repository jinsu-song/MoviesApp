package com.songjinsu.moviesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.songjinsu.moviesapp.data.db.dao.FavoriteMovieDao
import com.songjinsu.moviesapp.data.db.entities.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao() : FavoriteMovieDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "movie_database"
                ).build()
                AppDatabase.instance = instance
                instance
            }
        }
    }
}
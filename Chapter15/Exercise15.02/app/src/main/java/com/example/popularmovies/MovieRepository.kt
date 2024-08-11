package com.example.popularmovies

import android.util.Log
import com.example.popularmovies.database.MovieDao
import com.example.popularmovies.database.MovieDatabase
import com.example.popularmovies.model.Movie
import com.example.popularmovies.network.MovieService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MovieRepository(
    private val movieService: MovieService,
    private val movieDatabase: MovieDatabase,
) {

    suspend fun getPopularMovies(): Flow<List<Movie>> {
        val movieDao: MovieDao = movieDatabase.movieDao()

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                val movies = movieService.getPopularMovies()
                movieDao.addMovies(movies)
            } catch (exception: Exception) {
                Log.e("MovieRepository", exception.toString())
            }
        }
        return movieDao.getMovies()
    }
}
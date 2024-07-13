package com.example.popularmovies

import com.example.popularmovies.model.Movie
import com.example.popularmovies.network.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(private val movieService: MovieService) {

    suspend fun getPopularMovies(): Flow<List<Movie>> {
        return flow {
            emit(movieService.getPopularMovies())
        }.flowOn(Dispatchers.IO)
    }
}
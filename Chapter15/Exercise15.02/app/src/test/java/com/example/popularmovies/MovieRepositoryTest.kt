package com.example.popularmovies

import android.util.Log
import app.cash.turbine.test
import com.example.popularmovies.database.MovieDao
import com.example.popularmovies.database.MovieDatabase
import com.example.popularmovies.model.Movie
import com.example.popularmovies.network.MovieService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Calendar

class MovieRepositoryTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var movieService: MovieService

    @MockK
    lateinit var movieDatabase: MovieDatabase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPopularMovies() {
        val localMovie = Movie(
            title = "Local Movie",
            releaseDate = Calendar.getInstance().get(Calendar.YEAR).toString()
        )
        val remoteMovie = Movie(
            title = "Remote Movie",
            releaseDate = Calendar.getInstance().get(Calendar.YEAR).toString()
        )

        val allMovies = mutableListOf(localMovie)
        val movieDao = object : MovieDao {
            override fun addMovies(movies: List<Movie>) {
                allMovies.addAll(movies)
            }

            override fun getMovies() = flowOf(allMovies)
        }
        every { movieDatabase.movieDao() } returns movieDao

        val movieRepository = MovieRepository(movieService, movieDatabase)
        coEvery { movieService.getPopularMovies() } returns listOf(remoteMovie)

        val dispatcher = UnconfinedTestDispatcher()
        runTest {
            movieRepository.getPopularMovies()
                .flowOn(dispatcher)
                .test {
                    assertEquals(listOf(localMovie, remoteMovie), awaitItem())
                    awaitComplete()
                }
        }
    }

    @Test
    fun getPopularMoviesError() {
        val exception = "Test Exception"

        val savedMovies = emptyList<Movie>()
        val movieDao = object : MovieDao {
            override fun addMovies(movies: List<Movie>) {}

            override fun getMovies() = flowOf(savedMovies)
        }
        every { movieDatabase.movieDao() } returns movieDao

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        val movieRepository = MovieRepository(movieService, movieDatabase)
        coEvery { movieService.getPopularMovies() } throws RuntimeException(exception)

        runTest {
            movieRepository.getPopularMovies().test {
                assertEquals(savedMovies, awaitItem())
                awaitComplete()
            }
        }
    }
}
package com.example.popularmovies

import app.cash.turbine.test
import com.example.popularmovies.model.Movie
import com.example.popularmovies.network.MovieService
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
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

    @Test
    fun getPopularMovies() {
        val movies = listOf(
            Movie(
                title = "Title",
                releaseDate = Calendar.getInstance().get(Calendar.YEAR).toString()
            )
        )

        val movieRepository = MovieRepository(movieService)

        runTest {
            coEvery { movieService.getPopularMovies()  } returns movies

            movieRepository.getPopularMovies().test {
                assertEquals(movies, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun getPopularMoviesError() {
        val exception = "Test Exception"
        val movieRepository = MovieRepository(movieService)

        runTest {
            coEvery { movieService.getPopularMovies()  } throws RuntimeException(exception)

            movieRepository.getPopularMovies().test {
                assertEquals(exception, awaitError().message)
            }
        }
    }
}
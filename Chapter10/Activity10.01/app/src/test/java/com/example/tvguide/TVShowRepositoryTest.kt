package com.example.tvguide

import app.cash.turbine.test
import com.example.tvguide.model.TVShow
import com.example.tvguide.network.TVShowService
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Calendar

class TVShowRepositoryTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var tvShowService: TVShowService

    @Test
    fun getTVShows() {
        val tvShows = listOf(
            TVShow(
                name = "Title",
                firstAirDate = Calendar.getInstance().get(Calendar.YEAR).toString()
            )
        )

        val tvShowRepository = TVShowRepository(tvShowService)

        runTest {
            coEvery { tvShowService.getTVShows()  } returns tvShows

            tvShowRepository.getTVShows().test {
                assertEquals(tvShows, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun getTVShowsError() {
        val exception = "Test Exception"
        val tvShowRepository = TVShowRepository(tvShowService)

        runTest {
            coEvery { tvShowService.getTVShows()  } throws RuntimeException(exception)

            tvShowRepository.getTVShows().test {
                assertEquals(exception, awaitError().message)
            }
        }
    }
}
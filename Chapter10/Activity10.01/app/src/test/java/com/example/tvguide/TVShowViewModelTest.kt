package com.example.tvguide

import com.example.tvguide.model.TVShow
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Calendar

class TVShowViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var tvShowRepository: TVShowRepository

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun getTVShows() {
        val TVShows = listOf(
            TVShow(
                name = "Title",
                firstAirDate = Calendar.getInstance().get(Calendar.YEAR).toString()
            )
        )

        val tvShowViewModel = TVShowViewModel(tvShowRepository, testDispatcher)

        runTest {
            coEvery { tvShowRepository.getTVShows() } returns flowOf(TVShows)

            tvShowViewModel.getTVShows()
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(TVShows, tvShowViewModel.tvShows.value)
        }
    }
}
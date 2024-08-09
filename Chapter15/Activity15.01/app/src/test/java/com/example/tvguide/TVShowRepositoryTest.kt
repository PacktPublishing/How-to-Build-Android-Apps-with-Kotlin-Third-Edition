package com.example.tvguide

import android.util.Log
import app.cash.turbine.test
import com.example.tvguide.database.TVShowDao
import com.example.tvguide.database.TVShowDatabase
import com.example.tvguide.model.TVShow
import com.example.tvguide.network.TVShowService
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

class TVShowRepositoryTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var tvShowService: TVShowService

    @MockK
    lateinit var tvShowDatabase: TVShowDatabase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPopularTVShows() {
        val localTVShow = TVShow(
            name = "Local TVShow",
            firstAirDate = Calendar.getInstance().get(Calendar.YEAR).toString()
        )
        val remoteTVShow = TVShow(
            name = "Remote TVShow",
            firstAirDate = Calendar.getInstance().get(Calendar.YEAR).toString()
        )

        val allTVShows = mutableListOf(localTVShow)
        val tvShowDao = object : TVShowDao {
            override fun addTVShows(tvShows: List<TVShow>) {
                allTVShows.addAll(tvShows)
            }

            override fun getTVShows() = flowOf(allTVShows)
        }
        every { tvShowDatabase.tvShowDao() } returns tvShowDao

        val tvShowRepository = TVShowRepository(tvShowService, tvShowDatabase)
        coEvery { tvShowService.getTVShows() } returns listOf(remoteTVShow)

        val dispatcher = UnconfinedTestDispatcher()
        runTest {
            tvShowRepository.getTVShows()
                .flowOn(dispatcher)
                .test {
                    assertEquals(listOf(localTVShow, remoteTVShow), awaitItem())
                    awaitComplete()
                }
        }
    }

    @Test
    fun getPopularTVShowsError() {
        val exception = "Test Exception"

        val savedTVShows = mutableListOf(
            TVShow(
                name = "Local TVShow",
                firstAirDate = Calendar.getInstance().get(Calendar.YEAR).toString()
            )
        )
        val tvShowDao = object : TVShowDao {
            override fun addTVShows(tvShows: List<TVShow>) {
                savedTVShows.addAll(tvShows)
            }

            override fun getTVShows() = flowOf(savedTVShows)
        }
        every { tvShowDatabase.tvShowDao() } returns tvShowDao

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        val tvShowRepository = TVShowRepository(tvShowService, tvShowDatabase)
        runTest {
            coEvery { tvShowService.getTVShows() } throws RuntimeException(exception)

            tvShowRepository.getTVShows().test {
                assertEquals(savedTVShows, awaitItem())
                awaitComplete()
            }
        }
    }
}
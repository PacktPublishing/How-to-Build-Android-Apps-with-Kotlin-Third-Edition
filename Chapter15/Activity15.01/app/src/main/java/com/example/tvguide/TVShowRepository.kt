package com.example.tvguide

import android.util.Log
import com.example.tvguide.database.TVShowDao
import com.example.tvguide.database.TVShowDatabase
import com.example.tvguide.model.TVShow
import com.example.tvguide.network.TVShowService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TVShowRepository(
    private val tvShowService: TVShowService,
    private val tvShowDatabase: TVShowDatabase
) {

    suspend fun getTVShows(): Flow<List<TVShow>> {
        val tvShowDao: TVShowDao = tvShowDatabase.tvShowDao()

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                val tvShows = tvShowService.getTVShows()
                tvShowDao.addTVShows(tvShows)
            } catch (exception: Exception) {
                Log.e("TVShowRepository", exception.toString())
            }
        }
        return tvShowDao.getTVShows()
    }
}
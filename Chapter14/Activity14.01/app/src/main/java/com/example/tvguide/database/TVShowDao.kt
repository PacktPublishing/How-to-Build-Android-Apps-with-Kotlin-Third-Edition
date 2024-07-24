package com.example.tvguide.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tvguide.model.TVShow
import kotlinx.coroutines.flow.Flow

@Dao
interface TVShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTVShows(tvShows: List<TVShow>)

    @Query("SELECT * FROM tvshows")
    fun getTVShows(): Flow<List<TVShow>>
}
package com.packt.android.storage.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogs(dogs: List<DogEntity>)

    @Query("SELECT * FROM dogs")
    fun loadDogs(): Flow<List<DogEntity>>

    @Query("DELETE FROM dogs")
    suspend fun deleteAll()
}
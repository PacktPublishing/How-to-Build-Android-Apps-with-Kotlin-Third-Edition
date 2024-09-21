package com.packt.android

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM notes")
    fun loadNotes(): Flow<List<Note>>

    @Query("SELECT count(*) FROM notes")
    fun loadNoteCount(): Flow<Int>
}
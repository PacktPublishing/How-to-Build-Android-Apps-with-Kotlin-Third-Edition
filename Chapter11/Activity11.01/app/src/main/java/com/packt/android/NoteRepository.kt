package com.packt.android

import kotlinx.coroutines.flow.Flow


interface NoteRepository {

    suspend fun insertNote(note: Note)

    fun getAllNotes(): Flow<List<Note>>

    fun getNoteCount(): Flow<Int>
}
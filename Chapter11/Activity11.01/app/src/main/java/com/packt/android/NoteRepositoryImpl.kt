package com.packt.android

import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    override fun getAllNotes(): Flow<List<Note>> = noteDao.loadNotes()

    override fun getNoteCount(): Flow<Int> = noteDao.loadNoteCount()
}
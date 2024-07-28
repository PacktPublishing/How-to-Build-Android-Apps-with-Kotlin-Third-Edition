package com.packt.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            noteRepository.getAllNotes().combine(noteRepository.getNoteCount()) { notes, count ->
                UiState(notes = notes.map { it.text }, noteCount = count)
            }.collectLatest {
                _state.emit(it)
            }
        }
    }

    fun insertNote(text: String) {
        viewModelScope.launch {
            noteRepository.insertNote(Note(id = 0, text = text))
        }
    }

    data class UiState(
        val notes: List<String> = emptyList(),
        val noteCount: Int = 0
    )
}
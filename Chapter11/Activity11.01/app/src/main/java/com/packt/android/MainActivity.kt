package com.packt.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.packt.android.ui.theme.Activity1101Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val noteDatabase =
            Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "notes-db")
                .build()
        val noteRepository = NoteRepositoryImpl(noteDatabase.noteDao())
        setContent {
            Activity1101Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel =
                        viewModel<NoteViewModel>(factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return NoteViewModel(noteRepository) as T
                            }
                        })
                    Note(viewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Note(
    viewModel: NoteViewModel,
    modifier: Modifier = Modifier
) {
    NoteScreen(
        uiState = viewModel.state.collectAsState().value,
        onNewNoteClicked = {
            viewModel.insertNote(it)
        }, modifier = modifier
    )
}

@Composable
fun NoteScreen(
    uiState: NoteViewModel.UiState,
    onNewNoteClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            var textFieldText by remember {
                mutableStateOf("")
            }
            TextField(value = textFieldText, onValueChange = {
                textFieldText = it
            })
            Button(onClick = {
                onNewNoteClicked(textFieldText)
            }) {
                Text(text = stringResource(id = R.string.click_me))
            }
            Text(text = stringResource(id = R.string.total_notes, uiState.noteCount))
        }
        items(uiState.notes.size) {
            Text(text = uiState.notes[it])
        }

    }
}

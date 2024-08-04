package com.packt.android

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.packt.android.ui.theme.Exercise1202Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val preferenceStore = PreferenceStore(applicationContext)
        setContent {
            Exercise1202Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = viewModel<PreferenceViewModel>(factory = object :
                        ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return PreferenceViewModel(preferenceStore) as T
                        }
                    })
                    Preference(
                        preferenceViewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Preference(
    preferenceViewModel: PreferenceViewModel,
    modifier: Modifier = Modifier
) {
    PreferenceScreen(
        uiState = preferenceViewModel.state.collectAsState().value, onButtonClicked = {
            preferenceViewModel.saveText(it)
        },
        modifier = modifier
    )
}

@Composable
fun PreferenceScreen(
    uiState: PreferenceViewModel.UiState,
    onButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        var textFieldText by remember {
            mutableStateOf("")
        }
        TextField(value = textFieldText, onValueChange = {
            textFieldText = it
        })
        Button(onClick = {
            onButtonClicked(textFieldText)
        }) {
            Text(text = stringResource(id = R.string.click_me))
        }
        Text(text = uiState.text)
    }
}
package com.packt.android

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.packt.android.ui.theme.Exercise1102Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Exercise1102Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Main(
    totalViewModel: TotalViewModel = viewModel(),
    modifier: Modifier
) {
    MainScreen(modifier = modifier,
        state =  totalViewModel.state.collectAsState().value,
        onButtonClick = {
            totalViewModel.incrementResult()
        })
}

@Composable
fun MainScreen(
    modifier: Modifier,
    state: TotalViewModel.UiState,
    onButtonClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.total_count, state.result),
            color = state.textColor
        )
        Button(onClick = onButtonClick) {
            Text(text = stringResource(id = R.string.click_me))
        }
    }
}

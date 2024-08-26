package com.packt.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.packt.android.ui.theme.Exercise1301Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainContainer =
            MainContainer((application as MainApplication).applicationContainer.numberRepository)
        enableEdgeToEdge()
        setContent {
            Exercise1301Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        mainViewModel = viewModel(factory = mainContainer.getMainViewModelFactory()),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Main(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val number = mainViewModel.state.collectAsState().value
    MainScreen(number = number, onButtonClick = {
        mainViewModel.generateNextNumber()
    }, modifier)
}

@Composable
fun MainScreen(
    number: Int,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$number"
        )
        Button(onClick = onButtonClick) {
            Text(
                text = stringResource(id = R.string.randomize)
            )
        }

    }
}


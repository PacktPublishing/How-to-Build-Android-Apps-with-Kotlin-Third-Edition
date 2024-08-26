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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.packt.android.ui.theme.Exercise1302Theme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MainApplication)
            .applicationComponent
            .createMainSubcomponent()
            .inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Exercise1302Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        mainViewModel = viewModel(factory = factory),
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
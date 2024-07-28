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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.packt.android.ui.theme.Exercise1101Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Exercise1101Theme {
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
    var count by remember {
        mutableStateOf(totalViewModel.result)
    }
    MainScreen(modifier = modifier,
        count = count,
        onButtonClick = {
            totalViewModel.incrementResult()
            count = totalViewModel.result
        })
}

@Composable
fun MainScreen(
    modifier: Modifier,
    count: Int = 0,
    onButtonClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(text = stringResource(id = R.string.total_count, count))
        Button(onClick = onButtonClick) {
            Text(text = stringResource(id = R.string.click_me))
        }
    }
}

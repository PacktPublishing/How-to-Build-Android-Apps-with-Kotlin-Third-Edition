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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.packt.android.ui.theme.Exercise0903Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val randomizer = (application as MyApplication).randomizer
        setContent {
            Exercise0903Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var result by remember { mutableStateOf("") }
                    MainScreen(
                        resultText = result,
                        onButtonClick = {
                            result = getString(
                                R.string.generated_number,
                                randomizer.getNumber()
                            )
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    resultText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Button(onClick = onButtonClick) { Text(text = "Press Me") }
        Text(text = resultText)
    }
}
package com.packt.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.packt.android.ui.theme.Exercise0902Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val numberAdder = NumberAdder()
        val textFormatter = TextFormatter(numberAdder, applicationContext)
        setContent {
            Exercise0902Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var textFieldText by remember { mutableStateOf("") }
                    var resultText by remember { mutableStateOf("") }
                    MainScreen(
                        textFieldText = textFieldText,
                        onTextFieldValueChange = { textFieldText = it },
                        resultText = resultText,
                        onButtonClick = {
                            textFormatter.getSumResult(textFieldText.toInt()) {
                                resultText = it
                            }
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
    textFieldText: String,
    onTextFieldValueChange: (String) -> Unit,
    resultText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        TextField(
            value = textFieldText,
            onValueChange = onTextFieldValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Text Field") }
        )
        Button(onClick = onButtonClick) { Text(text = "Press Me") }
        Text(text = resultText)
    }
}
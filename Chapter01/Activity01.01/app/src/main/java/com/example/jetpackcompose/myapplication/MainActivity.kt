package com.example.jetpackcompose.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color as ComposeColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorCreatorScreen()
        }
    }
}

@Composable
fun ColorCreatorScreen() {
    var redChannel by remember { mutableStateOf("") }
    var greenChannel by remember { mutableStateOf("") }
    var blueChannel by remember { mutableStateOf("") }
    var colorToDisplay by remember { mutableStateOf(ComposeColor.White) }

    val context = LocalContext.current

    // Function to filter input to only allow hexadecimal characters
    fun filterHexInput(input: String): String {
        return input.filter { it in '0'..'9' || it in 'A'..'F' || it in 'a'..'f' }.take(2)
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Add two hexadecimal characters between 0-9, A-F or a-f without the '#' for each channel")
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = redChannel,
                onValueChange = { redChannel = it },
                label = { Text("Red Channel") }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = greenChannel,
                onValueChange = { greenChannel = it },
                label = { Text("Green Channel") }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = blueChannel,
                onValueChange = { blueChannel = it },
                label = { Text("Blue Channel") }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    // Check that all fields are filled in correctly and show error message if not.
                    if (isValidHexInput(redChannel) && isValidHexInput(greenChannel) && isValidHexInput(blueChannel)) {
                        val colorString = "#$redChannel$greenChannel$blueChannel"
                        colorToDisplay = try {
                            ComposeColor(android.graphics.Color.parseColor(colorString))
                        } catch (e: IllegalArgumentException) {
                            ComposeColor.White
                        }

                    } else {
                        Toast.makeText(context, "Input text is invalid", Toast.LENGTH_LONG).show()
                    }
                }) {
                Text(stringResource(id = R.string.create_rgb_color))
            }

            Text(
                modifier = Modifier.background(colorToDisplay).padding(24.dp),
                text = "Created color display panel"
            )
        }
    }
}

fun isValidHexInput(input: String): Boolean {
    return input.filter { it in '0'..'9' || it in 'A'..'F' || it in 'a'..'f' }.length == 2
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        ColorCreatorScreen()
    }
}

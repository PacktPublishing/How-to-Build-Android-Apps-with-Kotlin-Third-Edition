package com.example.activityresults

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activityresults.ui.theme.ActivityResultsTheme

class ColorPickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActivityResultsTheme {
                ColorPickerScreen()
            }
        }
    }

    @Composable
    private fun ColorPickerScreen() {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {

                val clickHandler = { color: Long, colorName: String ->
                    setRainbowColor(color, colorName)
                }

                Text(
                    stringResource(id = R.string.header_text_picker),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                )
                RainbowColor(0xFFFF0000L, getString(R.string.red), clickHandler)
                RainbowColor(0xFFFFA500L, getString(R.string.orange), clickHandler)
                RainbowColor(0xFFFFEE00L, getString(R.string.yellow), clickHandler)
                RainbowColor(0xFF00FF00L, getString(R.string.green), clickHandler)
                RainbowColor(0xFF0000FFL, getString(R.string.blue), clickHandler)
                RainbowColor(0xFF4B0082L, getString(R.string.indigo), clickHandler)
                RainbowColor(0xFF8A2BE2L, getString(R.string.violet), clickHandler)
            }
        }
    }

    private fun setRainbowColor(color: Long, colorName: String) {
        Intent().let { pickedColorIntent ->
            pickedColorIntent.putExtra(RAINBOW_COLOR_NAME, colorName)
            pickedColorIntent.putExtra(RAINBOW_COLOR, color)
            setResult(RESULT_OK, pickedColorIntent)
            finish()
        }
    }
}

@Composable
fun RainbowColor(color: Long, colorName: String, onButtonClick: (Long, String) -> Unit) {
    Button(
        onClick = { onButtonClick(color, colorName) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color), // Background color
            contentColor = Color.Black
        ),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    )
    {
        Text(
            text = colorName,
            color = Color.White,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview
@Composable
fun RainbowColorPreview() {
    RainbowColor(0xFF00FF00, "GREEN") { color, name -> }
}

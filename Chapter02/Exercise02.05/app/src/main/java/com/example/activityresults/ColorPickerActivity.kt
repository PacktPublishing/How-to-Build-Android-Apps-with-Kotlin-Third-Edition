package com.example.activityresults

import android.content.Context
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
                RainbowColor(red, getString(R.string.red), clickHandler)
                RainbowColor(orange, getString(R.string.orange), clickHandler)
                RainbowColor(yellow, getString(R.string.yellow), clickHandler)
                RainbowColor(green, getString(R.string.green), clickHandler)
                RainbowColor(blue, getString(R.string.blue), clickHandler)
                RainbowColor(indigo, getString(R.string.indigo), clickHandler)
                RainbowColor(violet, getString(R.string.violet), clickHandler)
                Text(
                    stringResource(id = R.string.footer_text_picker),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                )
            }
        }
    }

    companion object {
        private const val red = 0xFFFF0000L
        private const val orange = 0xFFFFA500L
        private const val yellow = 0xFFFFEE00L
        private const val green = 0xFF00FF00L
        private const val blue = 0xFF0000FFL
        private const val indigo = 0xFF4B0082L
        private const val violet = 0xFF8A2BE2L

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

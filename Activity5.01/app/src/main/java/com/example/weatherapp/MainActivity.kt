package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catagentprofile.LoadedImage
import com.example.weatherapp.model.ServerResponseData
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.ui.theme.WeatherAppTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var weather: WeatherData by remember {
                mutableStateOf(
                    WeatherData(
                        shortDescription = "",
                        longDescription = "",
                        iconId = ""
                    )
                )
            }

            LaunchedEffect(true) {
                withContext(Dispatchers.IO) {
                    weather = try {
                        val response = weather()
                        val weather = response.weather.firstOrNull()
                        if (weather == null) {
                            WeatherData(
                                shortDescription = "No weather returned.",
                                longDescription = "",
                                iconId = ""
                            )
                        } else {
                            weather
                        }
                    } catch (exception: Exception) {
                        WeatherData(
                            shortDescription = "Something went wrong",
                            longDescription = "$exception",
                            iconId = ""
                        )
                    }
                }
            }

            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Card(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Weather(
                            shortDescription = weather.shortDescription,
                            longDescription = weather.longDescription,
                            weatherIconUrl = weather.iconId.let { iconId ->
                                if (iconId.isEmpty()) {
                                    ""
                                } else {
                                    "https://openweathermap.org/img/wn/$iconId@2x.png"
                                }
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

    private suspend fun weather(): ServerResponseData = client.get(
        "https://api.openweathermap.org/data/2.5/weather"
    ) {
        url {
            parameter("appid", "[ENTER TOKEN HERE]")
            parameter("lat", "51.6201654")
            parameter("lon", "0.3018662")
        }
    }.body()
}

@Composable
fun Weather(
    shortDescription: String,
    longDescription: String,
    weatherIconUrl: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = shortDescription,
                    fontSize = 18.sp
                )
                Text(text = longDescription)
            }
            if (weatherIconUrl.isNotEmpty()) {
                LoadedImage(imageUrl = weatherIconUrl)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Weather(
            shortDescription = "Short",
            longDescription = "Long",
            weatherIconUrl = ""
        )
    }
}

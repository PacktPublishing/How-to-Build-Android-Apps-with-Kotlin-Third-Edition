package com.example.catagentprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.catagentprofile.model.ImageResultData
import com.example.catagentprofile.ui.theme.CatAgentProfileTheme
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
            var serverResponse by remember {
                mutableStateOf("Loading...")
            }
            LaunchedEffect(true) {
                withContext(Dispatchers.IO) {
                    serverResponse = try {
                        val results = searchImages(5)
                        if (results.isEmpty()) {
                            "No results."
                        } else {
                            results.map { it.imageUrl }.firstOrNull() ?: "No URLs"
                        }
                    } catch (exception: Exception) {
                        "Error: $exception"
                    }
                }
            }

            CatAgentProfileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ServerResponse(
                        contents = serverResponse,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private suspend fun searchImages(limit: Int): List<ImageResultData> =
        client.get(
            "https://api.thecatapi.com/v1/images/search"
        ) {
            url {
                parameter("limit", limit)
            }
        }.body()
}

@Composable
fun ServerResponse(contents: String, modifier: Modifier = Modifier) {
    Text(
        text = contents,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CatAgentProfileTheme {
        ServerResponse("Android")
    }
}

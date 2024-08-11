package com.example.tvguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tvguide.ui.DetailsScreen
import com.example.tvguide.ui.theme.TVGuideTheme

class DetailsActivity : ComponentActivity() {

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_RELEASE = "release"
        const val EXTRA_OVERVIEW = "overview"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        const val EXTRA_POSTER = "poster"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TVGuideTheme {
                val extras = intent.extras
                val posterPath = extras?.getString(EXTRA_POSTER).orEmpty()

                DetailsScreen(
                    title = extras?.getString(EXTRA_TITLE).orEmpty(),
                    release = extras?.getString(EXTRA_RELEASE).orEmpty().take(4),
                    overview = extras?.getString(EXTRA_OVERVIEW).orEmpty(),
                    image = "$IMAGE_URL$posterPath",
                    onBackButtonClick = { finish() }
                )
            }
        }
    }
}
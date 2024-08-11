package com.example.tvguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tvguide.model.TVShow
import com.example.tvguide.ui.MainScreen
import com.example.tvguide.ui.theme.TVGuideTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TVGuideTheme {
                MainScreen { tvShow ->
                    openTVShowDetails(tvShow)
                }
            }
        }
    }

    private fun openTVShowDetails(tvShow: TVShow) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_TITLE, tvShow.name)
            putExtra(DetailsActivity.EXTRA_RELEASE, tvShow.firstAirDate)
            putExtra(DetailsActivity.EXTRA_OVERVIEW, tvShow.overview)
            putExtra(DetailsActivity.EXTRA_POSTER, tvShow.posterPath)
        }
        startActivity(intent)
    }
}
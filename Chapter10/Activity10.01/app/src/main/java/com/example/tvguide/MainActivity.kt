package com.example.tvguide

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tvguide.model.TVShow
import com.example.tvguide.ui.TVShowItemView
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: TVShowViewModel = viewModel(factory = TVShowViewModel.Factory),
    onSelectTVShow: (TVShow) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getTVShows()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = topAppBarColors(MaterialTheme.colorScheme.primary),
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val tvShows = viewModel.tvShows.collectAsState()
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(tvShows.value) { tvShow ->
                    TVShowItemView(tvShow = tvShow) {
                        onSelectTVShow(tvShow)
                    }
                }
            }

            val error = viewModel.error.collectAsState()
            if (error.value.isNotEmpty()) {
                Text(
                    text = error.value,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    TVGuideTheme {
        MainScreen {}
    }
}
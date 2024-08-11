package com.example.popularmovies.ui

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.popularmovies.MovieViewModel
import com.example.popularmovies.R
import com.example.popularmovies.model.Movie
import com.example.popularmovies.ui.theme.PopularMoviesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory),
    onSelectMovie: (Movie) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getPopularMovies()
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
            val movies = viewModel.popularMovies.collectAsState()
            LazyVerticalGrid(columns = GridCells.Adaptive(160.dp)) {
                items(movies.value) { movie ->
                    MovieItemView(movie = movie) {
                        onSelectMovie(movie)
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
    PopularMoviesTheme {
        MainScreen {}
    }
}